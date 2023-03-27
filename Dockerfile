FROM ubuntu:20.04

# Environment variables
ENV RISCV=/opt/riscv
ENV PATH=$PATH:$RISCV/bin
ENV MAKEFLAGS="-j4"

# Set geographic location
ENV TZ=Asia/Tokyo
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

WORKDIR ${RISCV}

# Install dependencies
RUN apt-get update && apt-get install -y \
  autoconf \
  automake \
  autotools-dev \
  curl \
  libmpc-dev \
  libmpfr-dev \
  libgmp-dev \
  gawk \
  build-essential \
  bison \
  flex \
  texinfo \
  gperf \
  libtool \
  patchutils \
  bc \
  zlib1g-dev \
  libexpat-dev \
  git \
  pkg-config \
  libusb-1.0-0-dev \
  libftdi-dev \
  device-tree-compiler \
  default-jdk \
  gnupg \
  vim \
  python3 \
  python3-pip

# Install RISC-V toolchain
RUN git clone -b rvv-next --single-branch https://github.com/riscv/riscv-gnu-toolchain.git && \
  cd riscv-gnu-toolchain && \
  git submodule update --init --recursive

RUN cd riscv-gnu-toolchain && \
  mkdir build && \
  cd build && \
  ../configure --prefix=${RISCV} --enable-multilib && \
  make

# Install RISC-V Test
RUN git clone -b master --single-branch https://github.com/riscv/riscv-tests && \
  cd riscv-tests && git checkout c4217d88bce9f805a81f42e86ff56ed363931d69 && \
  git submodule update --init --recursive

# sbt
RUN echo "deb https://repo.scala-sbt.org/scalasbt/debian all main" | tee -a /etc/apt/sources.list.d/sbt.list && \
  echo "deb https://repo.scala-sbt.org/scalasbt/debian /" | tee /etc/apt/sources.list.d/sbt_old.list && \
  curl -sL "https://keyserver.ubuntu.com/pks/lookup?op=get&search=0x2EE0EA64E40A89B84B2DF73499E82A75642AC823" | apt-key add && \
  apt-get update && apt-get install -y sbt