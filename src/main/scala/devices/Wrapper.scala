package devices

import chisel3._
import chisel3.util._

class Wrapper extends Module {
  val io = IO(new Bundle {
    val exit = Output(Bool())
  })

  val cpu = Module(new CPU())
  val memory = Module(new Memory())

  cpu.io.imem <> memory.io.imem

  io.exit := cpu.io.exit
}