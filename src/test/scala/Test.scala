package devices

import chisel3._
import chisel3.util._

import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class WrapperSpec extends AnyFlatSpec with ChiselScalatestTester{
  behavior of "Wrapper"

  it should "pass" in {
    test(new Wrapper) { c =>
      while (!c.io.exit.peek().litToBoolean) {
        c.clock.step(1)
      }
    }
  }
}