/*
 * Copyright © 2016 Amdocs Software Systems Limited
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

package com.amdocs.zusammen.adaptor.outbound.impl.convertor;

import com.amdocs.zusammen.core.api.types.CoreElement;
import com.amdocs.zusammen.core.api.types.CoreElementConflict;
import com.amdocs.zusammen.datatypes.Id;
import com.amdocs.zusammen.datatypes.item.ElementContext;
import com.amdocs.zusammen.sdk.collaboration.types.CollaborationElement;
import com.amdocs.zusammen.sdk.collaboration.types.CollaborationElementConflict;

import java.util.stream.Collectors;

public class CollaborationElementConvertor {

  public static CollaborationElement convertFromCoreElement(CoreElement element,
                                                            ElementContext elementContext) {
    CollaborationElement collaborationElement =
        new CollaborationElement(elementContext.getItemId(), elementContext.getVersionId(),
            element.getNamespace(), element.getId());
    collaborationElement.setParentId(element.getParentId());
    collaborationElement.setInfo(element.getInfo());
    collaborationElement.setRelations(element.getRelations());

    collaborationElement.setData(element.getData());
    collaborationElement.setSearchableData(element.getSearchableData());
    collaborationElement.setVisualization(element.getVisualization());
    return collaborationElement;
  }

  public static CoreElement convertToCoreElement(CollaborationElement element) {
    if (element == null) {
      return null;
    }
    CoreElement coreElement = convertToCoreElement(element.getId());
    coreElement.setNamespace(element.getNamespace());
    coreElement.setParentId(element.getParentId());
    coreElement.setInfo(element.getInfo());
    coreElement.setRelations(element.getRelations());

    coreElement.setData(element.getData());
    coreElement.setSearchableData(element.getSearchableData());
    coreElement.setVisualization(element.getVisualization());

    coreElement.setSubElements(element.getSubElements().stream()
        .map(CollaborationElementConvertor::convertToCoreElement)
        .collect(Collectors.toList()));
    return coreElement;
  }

  private static CoreElement convertToCoreElement(Id elementId) {
    CoreElement coreElement = new CoreElement();
    coreElement.setId(elementId);
    return coreElement;
  }

  public static CoreElementConflict convertToCoreElement(
      CollaborationElementConflict collaborationElementConflict) {
    CoreElementConflict coreElementConflict = new CoreElementConflict();
    coreElementConflict
        .setLocalElement(convertToCoreElement(collaborationElementConflict.getLocalElement()));
    coreElementConflict
        .setRemoteElement(convertToCoreElement(collaborationElementConflict.getRemoteElement()));
    return coreElementConflict;
  }
}
