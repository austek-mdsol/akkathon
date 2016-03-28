package com.medidata.strategicmonitoring.sitevisit

import java.time.Instant

import com.medidata.strategicmonitoring.api.{Key, KeyAndVersion}
import org.slf4j.LoggerFactory

//TOTALLY FAKED
//stateless singleton
//could talk to mysql, nosql, akka persistance etc
//could cache entities in memcahched etc
//point is rest of system doesnt know / care
//stateless singleton, needs to be transactional
//note returning entities / copy on write to facilitate higher level functions / methods
object SiteVisitDAO {

  val logger = LoggerFactory.getLogger(getClass())

  def create(newSiteVisit: NewSiteVisit) = {

    //call insert
    logger.info("SiteVisitDAO::create called, newSiteVisit=" + newSiteVisit)
  }

  def read(key: Key): ExistingSiteVisit = {

    //call select, get latest version
    logger.info("SiteVisitDAO::read called, key=" + key)
    new ExistingSiteVisit(new KeyAndVersion(key, 10000), "readKeyAAA", "readKeyBBB", Instant.now())
  }

  def read(keyAndVersion: KeyAndVersion): ExistingSiteVisit = {

    //call select, get latest version
    logger.info("SiteVisitDAO::read called, keyAndVersion=" + keyAndVersion)
    new ExistingSiteVisit(keyAndVersion, "readKeyAndVersionAAA", "readKeyAndVersionBBB", Instant.now())
  }

  def update(existingSiteVisit: ExistingSiteVisit) = {

    //call update
    logger.info("SiteVisitDAO::update called, existingSiteVisit=" + existingSiteVisit)
  }
}
