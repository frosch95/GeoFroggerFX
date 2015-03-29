/*
 * Copyright (c) Andreas Billmann <abi@geofroggerfx.de>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package de.geofroggerfx.dao;

import de.geofroggerfx.model.Cache;
import de.geofroggerfx.model.CacheListEntry;

import java.util.List;

/**
 * Implements the DAO Pattern for the cache objects.
 */
public interface CacheDAO {

    /**
     * this method should be used to batch save a list of caches.
     * existing caches will be deleted and inserted again
     * @param listOfCaches list of caches
     */
    void save(List<Cache> listOfCaches);

    /**
     * updates a single cache
     * @param cache cache to update
     */
    void update(Cache cache);

    /**
     * return CacheEntry (a reduced object for cache list)
     * @param name sorting name
     * @param asc sorting direction
     * @return list of CacheEntries
     */
    List<CacheListEntry> getAllCacheEntriesSortBy(String name, String asc);

    /**
     * returns the asked cache
     * @param id id of the cache
     * @return single cache
     */
    Cache getCacheForId(long id);

    /**
     * Returns all caches in a list (normally used by reporting plugins)
     * @return all caches
     */
    List<Cache> getAllCaches();

    /**
     * a list of caches filtered by where (normally used by reporting plugins)
     * @param where where clauses combined with AND
     * @return a list of filtered caches
     */
    List<Cache> getAllCaches(String... where);

}
