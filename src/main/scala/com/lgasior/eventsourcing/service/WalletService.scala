package com.lgasior.eventsourcing.service

import com.lgasior.eventsourcing.domain.wallet.Wallet

trait WalletService {

  def create(userId: String, name: String): Wallet

  def findAll(): List[Wallet]

  def findByUserId(userId: String): List[Wallet]

  def update(wallet: Wallet): Wallet

  def delete(wallet: Wallet): Unit

}
