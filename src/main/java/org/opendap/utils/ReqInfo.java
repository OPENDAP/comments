/*
 * /////////////////////////////////////////////////////////////////////////////
 * // This file is part of the "Hyrax Data Server" project.
 * //
 * //
 * // Copyright (c) 2013 OPeNDAP, Inc.
 * // Author: Nathan David Potter  <ndp@opendap.org>
 * //
 * // This library is free software; you can redistribute it and/or
 * // modify it under the terms of the GNU Lesser General Public
 * // License as published by the Free Software Foundation; either
 * // version 2.1 of the License, or (at your option) any later version.
 * //
 * // This library is distributed in the hope that it will be useful,
 * // but WITHOUT ANY WARRANTY; without even the implied warranty of
 * // MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * // Lesser General Public License for more details.
 * //
 * // You should have received a copy of the GNU Lesser General Public
 * // License along with this library; if not, write to the Free Software
 * // Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301
 * //
 * // You can contact OPeNDAP, Inc. at PO Box 112, Saunderstown, RI. 02874-0112.
 * /////////////////////////////////////////////////////////////////////////////
 */


package org.opendap.utils;

import org.slf4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * Provides utility methods that perform "analysis" of the user request and return important component strings
 * for the servlet.
 * @author Nathan Potter
 */

public class ReqInfo {

    private static Logger log;
    static {
        log = org.slf4j.LoggerFactory.getLogger(ReqInfo.class);

    }

    public static String getWebApplicationUrl(HttpServletRequest request) {
        String transport = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextName = request.getContextPath();

        // String webappUrl = transport + "://" + serverName + ":" + serverPort + contextName;

        StringBuilder webappUrl = new StringBuilder();
        webappUrl.append(transport).append("://").append(serverName);
        if (transport.equalsIgnoreCase("http") && serverPort != 80)
        {
            webappUrl.append(":").append(serverPort);
        }
        else if (transport.equalsIgnoreCase("https") && (serverPort != 443 && serverPort != 80))
        {
            webappUrl.append(":").append(serverPort);
        }
        webappUrl.append(contextName);

        log.debug("getWebApplicationUrl(): " + webappUrl.toString());
        return webappUrl.toString();
    }


    /**
     * Get service portion of the URL - everything in the URL before the localID (aka relativeUrl) of the dataset.
     * @param request The client request.
     * @return The URL of the request minus the last "." suffix. In other words if the requested URL ends
     * with a suffix that is preceeded by a dot (".") then the suffix will removed from this returned URL.
     */

    public static String getServiceUrl(HttpServletRequest request)  {
        String serviceUrl = getWebApplicationUrl(request) + request.getServletPath();
        log.debug("getServiceUrl(): "+serviceUrl);
        return serviceUrl;
    }


    /**
     * Deterimine the user id of the user associated with the currently porcessed request.
     * Some code (SpringBoot @Controllers for example) may not have an instance of HttpServletRequest
     * to pass in so that section will not run (and doesn't need to?)
     * @param request
     * @return
     */
    public static String getUserId(HttpServletRequest request){
        String userId = null;
        SecurityContext sc = SecurityContextHolder.getContext();
        if(sc!=null) {
            log.debug("getUserId() - Located SpringBoot SecurityContext: <{}>",sc);
            Authentication auth = sc.getAuthentication();
            if (auth != null) {
                log.debug("getUserId() - Located SpringBoot Authentication: <{}>",auth);
                log.debug((auth.getDetails().toString()));
                userId = auth.getPrincipal().toString();
            }
            else {
                log.debug("getUserId() - SecurityContext.getAuthentication() returned null. Unable to retrieve SpringBoot Authentication.");
            }
        }
        if(userId==null && request!=null){
            log.debug("getUserId() - Checking Tomcat API.");
            Principal userPrinciple = request.getUserPrincipal();
            if (request.getRemoteUser() != null) {
                userId = request.getRemoteUser();
                log.debug("getUserId() - request.getRemoteUser(): {}",userId);

            } else if (userPrinciple != null) {
                userId = userPrinciple.getName();
                log.debug("getUserId() - userPrinciple.getName(): {}",userId);
            }
            if(userId==null)
                log.debug("getUserId() - Tomcat API shows no active user.");
        }
        return userId;
    }
}                                                          


