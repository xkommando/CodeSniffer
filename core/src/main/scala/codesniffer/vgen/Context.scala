package codesniffer.vgen

import codesniffer.core.{Indexer, Location, VecWriter}

/**
 * Created by Bowen Cai on 4/12/2015.
 */
case class Context(config: Config, var currentLocation: Location, indexer: Indexer[String], vecWriter: VecWriter)
