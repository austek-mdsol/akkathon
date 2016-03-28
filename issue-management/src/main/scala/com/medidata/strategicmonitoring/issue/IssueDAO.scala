package com.medidata.strategicmonitoring.issue

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
object IssueDAO {

  val logger = LoggerFactory.getLogger(getClass())

  def create(newIssue: NewIssue) = {

    //call insert
    logger.info("IssueDAO::create called, newIssue=" + newIssue)
  }

  def read(key: Key): ExistingIssue = {

    //call select, get latest version
    logger.info("IssueDAO::read called, key=" + key)
    new ExistingIssue(new KeyAndVersion(key, 10000), "readKeyAAA", "readKeyBBB", Instant.now())
  }

  def read(keyAndVersion: KeyAndVersion): ExistingIssue = {

    //call select, get latest version
    logger.info("IssueDAO::read called, keyAndVersion=" + keyAndVersion)
    new ExistingIssue(keyAndVersion, "readKeyAndVersionAAA", "readKeyAndVersionBBB", Instant.now())
  }

  def update(existingIssue: ExistingIssue) = {

    //call update
    logger.info("IssueDAO::update called, existingIssue=" + existingIssue)
  }
}
