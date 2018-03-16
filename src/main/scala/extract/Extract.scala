package extract

trait Extract[T, U, R[_]] {
  def extract(t: T): R[U]
}

object Extract{

  type Identity[T] = T
  type ExtractIdentity[T,U] = Extract[T, U, Identity]
  type ExtractOption[T, U] = Extract[T, U, Option]

  type CoExtract[T, U, R[_]] = Extract[U, T, R]

  def apply[T, U, R[_]](f: T => R[U]): Extract[T, U, R] = new Extract[T, U, R] {
    override def extract(t: T): R[U] = f(t)
  }

  implicit def extractIdentityToOption[T, U](implicit extract: Extract[T, U, Identity]): Extract[T, U, Option] =
    Extract[T,U, Option] {t => Some(extract.extract(t))}
}