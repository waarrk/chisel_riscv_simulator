package devices

import chisel3._
import chisel3.util._
import chisel3.util.experimental.loadMemoryFromFile
import common.Define._

class ImemPortIo extends Bundle {
  val addr = Input(UInt(WORD_LEN.W))
  val inst = Output(UInt(WORD_LEN.W))
}

class Memory extends Module {
  val io = IO(new Bundle {
    val imem = new ImemPortIo()
  })

  val mem = Mem(MEMORY_LEN, UInt(BYTE_LEN.W))
  loadMemoryFromFile(mem, "src/main/resources/inst.txt")

  io.imem.inst := Cat(
    mem(io.imem.addr + 3.U(WORD_LEN.W)),
    mem(io.imem.addr + 2.U(WORD_LEN.W)),
    mem(io.imem.addr + 1.U(WORD_LEN.W)),
    mem(io.imem.addr)
  )
}