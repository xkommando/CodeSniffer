package codesniffer.codefunnel

/**
  * Created by Bowen Cai on 2/28/2016.
  */
trait Mapper[K,D] {
  
  def map(key: K, data: D, ctx:Context)

}
