package codesniffer.deckard.vgen

import codesniffer.deckard.{Indexer, Location, VecWriter}

/**
 * Created by Bowen Cai on 4/12/2015.
 */
case class Context[T](config: Config,
                      var currentLocation: Location,
                      var data: Option[AnyRef],
                      indexer: Indexer[T],
                      vecWriter: VecWriter[T])

