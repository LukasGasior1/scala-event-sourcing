package com.lgasior.eventsourcing

import org.springframework.scala.context.function.FunctionalConfigApplicationContext
import com.lgasior.eventsourcing.service.{WalletService, UserService}

object Main {

  def main(args: Array[String]) = {
    val ctx = FunctionalConfigApplicationContext(classOf[SpringConfiguration])
    ctx.start()

    val userService = ctx.getBean(classOf[UserService])
    val user = userService.create("lgasior", "pass1234")

    val walletService = ctx.getBean(classOf[WalletService])
    val wallet = walletService.create(user.id, "Business wallet")
    val renamedWallet = wallet.rename("Personal wallet")

    walletService.update(renamedWallet)

    val usersCount = userService.findAll().size
    val walletsCount = walletService.findAll().size

    println(s"""
      | Added user and wallet.
      | Current app state is: [users=$usersCount, wallets=$walletsCount].
      """.stripMargin)
  }

}
