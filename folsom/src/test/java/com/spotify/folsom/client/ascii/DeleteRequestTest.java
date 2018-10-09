/*
 * Copyright (c) 2015 Spotify AB
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.spotify.folsom.client.ascii;

import static org.junit.Assert.assertEquals;

import com.google.common.base.Charsets;
import com.spotify.folsom.MemcacheStatus;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import org.junit.Test;

public class DeleteRequestTest extends RequestTestTemplate {

  private DeleteRequest req = new DeleteRequest("foo".getBytes(Charsets.UTF_8));

  @Test
  public void testRequest() throws Exception {
    assertRequest(req, "delete foo\r\n");
  }

  @Test
  public void testResponse() throws IOException, InterruptedException, ExecutionException {
    req.handle(AsciiResponse.DELETED);

    assertEquals(MemcacheStatus.OK, req.get());
  }

  @Test
  public void testNonFoundResponse() throws IOException, InterruptedException, ExecutionException {
    req.handle(AsciiResponse.NOT_FOUND);
    assertEquals(MemcacheStatus.KEY_NOT_FOUND, req.get());
  }
}
