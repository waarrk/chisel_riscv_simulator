Chisel RISC-V Simulator
=======================

This repository is an unofficial RISC-V simulator using [freechipsproject/chisel-template](https://github.com/freechipsproject/chisel-template).

It is assumed that the necessary JDK is built and operated on Docker.

If you want to use Dockerfile, start with the following command.

```:sh
docker build . -t riscv/cpu
docker run -it -v ~/chisel_riscv_simulator:/src riscv/cpu
```
