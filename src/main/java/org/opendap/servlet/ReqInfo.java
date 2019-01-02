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


package org.opendap.servlet;

import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Provides utility methods that perform "analysis" of the user request and return important componet strings
 * for the OPeNDAP servlet.
 *
 * The dataSourceName is the local URL path of the request, minus any requestSuffixRegex detected. So, if the request is
 * for a dataset (an atom) then the dataSourceName is the local path and the name of the dataset minus the
 * requestSuffixRegex. If the request is for a collection, then the dataSourceName is the complete local path.
 * <p><b>Examples:</b>
 * <ul><li>If the complete URL were: http://opendap.org:8080/opendap/nc/fnoc1.nc.dds?lat,lon,time&lat>72.0<br/>
 * Then the:</li>
 * <ul>
 * <li> RequestURL = http://opendap.org:8080/opendap/nc/fnoc1.nc </li>
 * <li> CollectionName = /opendap/nc/ </li>
 * <li> DataSetName = fnoc1.nc </li>
 * <li> DataSourceName = /opendap/nc/fnoc1.nc </li>
 * <li> RequestSuffix = dds </li>
 * <li> ConstraintExpression = lat,lon,time&lat>72.0 </li>
 * </ul>
 *
 * <li>If the complete URL were: http://opendap.org:8080/opendap/nc/<br/>
 * Then the:</li>
 * <ul>
 * <li> RequestURL = http://opendap.org:8080/opendap/nc/ </li>
 * <li> CollectionName = /opendap/nc/ </li>
 * <li> DataSetName = null </li>
 * <li> DataSourceName = /opendap/nc/ </li>
 * <li> RequestSuffix = "" </li>
 * <li> ConstraintExpression = "" </li>
 * </ul>
 * </ul>
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


}                                                          


