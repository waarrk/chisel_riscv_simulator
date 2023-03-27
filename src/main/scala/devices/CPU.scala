package devices

import chisel3._
import chisel3.util._
import common.Define._

class CPU extends Module {
  val io = IO(new Bundle {
    val imem = Flipped(new ImemPortIo())
    val exit = Output(Bool())
  })

  // レジスタ生成 32bit * 32
  val reg = Mem(WORD_LEN, UInt(WORD_LEN.W))

  // 命令フェッチ
  val pc = RegInit(START_ADDR) // PCレジスタ
  pc := pc + 4.U(WORD_LEN.W) // PCレジスタの更新
  io.imem.addr := pc // PCレジスタの値をIMemのアドレスに設定

  val inst = io.imem.inst // IMemの出力をinstに設定

  io.exit := (inst === 0x34333231.U(WORD_LEN.W))
  // Debug
  printf(p"pc : 0x${Hexadecimal(pc)}\n")
  printf(p"inst   : 0x${Hexadecimal(inst)}\n")
  printf("---------\n")
}