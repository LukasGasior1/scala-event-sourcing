package com.lgasior.eventsourcing.service

import com.lgasior.eventsourcing.domain.user.User

trait UserService {

  def create(login: String, password: String): User

  def findAll(): List[User]

  def update(user: User): User

}
