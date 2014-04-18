package org.jtalks.pochta.http

import java.net.InetSocketAddress
import org.jtalks.pochta.config.ConfigLoader
import java.util.concurrent.Executors

/**
 *
 */
 public object HttpServer {

     val server = com.sun.net.httpserver.HttpServer.create()!!

     {
         val config = ConfigLoader.config;
         server.bind(InetSocketAddress(config.http.port), 0)
         setupResourceHandlers()
         server.setExecutor(Executors.newFixedThreadPool(config.http.threads))
         server.start()
         println("HTTP server listening on port ${config.http.port}")
     }

     fun setupResourceHandlers(){
         server.createContext("/inboxes/", MailListController)?.getFilters()?.add(TokenAuthenticationFilter)
         server.createContext("/css/", StaticController)
         server.createContext("/fonts/", StaticController)
         server.createContext("/img/", StaticController)
         server.createContext("/", MainPageController)
     }
 }