/*
 * Add Copyright © 2016-2017 European Support Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.amdocs.zusammen.sdk.searchindex.types;


import com.amdocs.zusammen.datatypes.Id;
import com.amdocs.zusammen.datatypes.Namespace;
import com.amdocs.zusammen.datatypes.Space;
import com.amdocs.zusammen.sdk.types.ElementDescriptor;
import com.amdocs.zusammen.utils.fileutils.FileUtils;

import java.io.InputStream;
import java.util.Objects;

public class SearchIndexElement extends ElementDescriptor {
  private Space space = Space.PRIVATE;
  private byte[] searchableData;

  public SearchIndexElement(Id itemId, Id versionId,
                            Namespace namespace,
                            Id id) {
    super(itemId, versionId, namespace, id);
  }

  public Space getSpace() {
    return space;
  }

  public void setSpace(Space space) {
    this.space = space;
  }

  public InputStream getSearchableData() {
    return Objects.isNull(searchableData) || searchableData.length == 0
        ? null
        : FileUtils.toInputStream(searchableData);
  }

  public void setSearchableData(InputStream searchableData) {
    this.searchableData = FileUtils.toByteArray(searchableData);
  }
}
