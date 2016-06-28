/**
 *
 * Created by superbow on 2/25/2016.
 *
 * 0.. create BD
 *      use sql scripts,
 *
 * 1. import project:
 *      manually insert poj basic info(id, lang, name, version, etc)
 *      use PojImport to insert dir structure, class info, etc.
 * 2. import method
 *      insertion of methods is done per project, so step 1 is to specify project (id, dir) in the XML
 *      use MethodImport4J to insert methods and src of the project
 *  3. match bellon data
 */
package codesniffer.codefunnel.crawler;