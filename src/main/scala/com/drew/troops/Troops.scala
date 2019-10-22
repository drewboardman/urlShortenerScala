package com.drew.troops

import cats.Applicative
import cats.implicits._
import io.circe.{Encoder, Json}
import org.http4s.EntityEncoder
import org.http4s.circe._

trait Troops[F[_]] {
  def hello(n: Troops.Name): F[Troops.Greeting]
}

object Troops {
  implicit def apply[F[_]](implicit ev: Troops[F]): Troops[F] = ev

  final case class Name(name: String) extends AnyVal

  /**
    * More generally you will want to decouple your edge representations from
    * your internal data structures, however this shows how you can
    * create encoders for your data.
    **/
  final case class Greeting(greeting: String) extends AnyVal
  object Greeting {
    implicit val greetingEncoder: Encoder[Greeting] = new Encoder[Greeting] {
      final def apply(a: Greeting): Json =
        Json.obj(("message", Json.fromString(a.greeting)))
    }
    implicit def greetingEntityEncoder[F[_]: Applicative]
      : EntityEncoder[F, Greeting] =
      jsonEncoderOf[F, Greeting]
  }

  def impl[F[_]: Applicative]: Troops[F] = new Troops[F] {
    def hello(n: Troops.Name): F[Troops.Greeting] =
      Greeting("Hello, " + n.name).pure[F]
  }
}
