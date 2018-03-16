package event

import extract.Extract
import extract.Extract._
import cats.{ApplicativeError, Functor}

trait VisitEvent[F[_], U] {
  def apply(e: Event)(implicit
                      f: Extract[Event1, U, F],
                      f2: Extract[Event2, U, F]): F[U] = e match {
    case e: Event1 => f.extract(e)
    case e: Event2 => f2.extract(e)
  }

  def coapply[E](u: U)(implicit applicativeError: ApplicativeError[F, E],
                       f: CoExtract[Event1, U, F],
                       f2: CoExtract[Event2, U, F]): F[Event] = {
    applicativeError.handleErrorWith(
      applicativeError.widen[Event1, Event](f.extract(u))
    )(e => applicativeError.widen[Event2, Event](f2.extract(u)))
  }
}

object VisitEvent {
  def apply[F[_], U]: VisitEvent[F, U] = new VisitEvent[F, U] {}
}
