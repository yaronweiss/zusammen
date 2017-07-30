/*
 * Copyright © 2016-2017 European Support Limited
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

package com.amdocs.zusammen.adaptor.inbound.impl.item;

import com.amdocs.zusammen.adaptor.inbound.api.item.ItemVersionAdaptor;
import com.amdocs.zusammen.adaptor.inbound.api.types.item.ItemVersionConflict;
import com.amdocs.zusammen.adaptor.inbound.api.types.item.MergeResult;
import com.amdocs.zusammen.adaptor.inbound.impl.convertor.ItemVersionConflictConvertor;
import com.amdocs.zusammen.adaptor.inbound.impl.convertor.MergeResultConvertor;
import com.amdocs.zusammen.commons.log.ZusammenLogger;
import com.amdocs.zusammen.commons.log.ZusammenLoggerFactory;
import com.amdocs.zusammen.core.api.item.ItemVersionManager;
import com.amdocs.zusammen.core.api.item.ItemVersionManagerFactory;
import com.amdocs.zusammen.core.api.types.CoreMergeResult;
import com.amdocs.zusammen.datatypes.Id;
import com.amdocs.zusammen.datatypes.SessionContext;
import com.amdocs.zusammen.datatypes.Space;
import com.amdocs.zusammen.datatypes.item.ItemVersion;
import com.amdocs.zusammen.datatypes.item.ItemVersionData;
import com.amdocs.zusammen.datatypes.item.ItemVersionStatus;
import com.amdocs.zusammen.datatypes.itemversion.ItemVersionHistory;
import com.amdocs.zusammen.datatypes.itemversion.Tag;
import com.amdocs.zusammen.datatypes.response.ErrorCode;
import com.amdocs.zusammen.datatypes.response.Module;
import com.amdocs.zusammen.datatypes.response.Response;
import com.amdocs.zusammen.datatypes.response.ReturnCode;
import com.amdocs.zusammen.datatypes.response.ZusammenException;

import java.util.Collection;

public class ItemVersionAdaptorImpl implements ItemVersionAdaptor {

  private static ZusammenLogger logger = ZusammenLoggerFactory.getLogger(ItemVersionAdaptorImpl
      .class.getName());

  @Override
  public Response<Collection<ItemVersion>> list(SessionContext context, Space space, Id itemId) {

    Response<Collection<ItemVersion>> response;
    try {
      Collection<ItemVersion> itemVersionCollection =
          getItemVersionManager(context).list(context, space, itemId);
      response = new Response<>(itemVersionCollection);
    } catch (ZusammenException ze) {
      logger.error(ze.getReturnCode().toString(), ze);
      response = new Response<>(ze.getReturnCode());
    }
    return response;


  }

  @Override
  public Response<ItemVersion> get(SessionContext context, Space space, Id itemId, Id versionId) {
    Response<ItemVersion> response;
    try {
      ItemVersion itemVersion =
          getItemVersionManager(context).get(context, space, itemId, versionId);
      response = new Response<>(itemVersion);
    } catch (ZusammenException ze) {
      ReturnCode returnCode =
          new ReturnCode(ErrorCode.ZU_ITEM_VERSION_GET, Module.ZDB, null, ze.getReturnCode());
      logger.error(returnCode.toString(), ze);
      response = new Response<>(returnCode);
    }
    return response;
  }

  @Override
  public Response<Id> create(SessionContext context, Id itemId, Id baseVersionId,
                             ItemVersionData data) {

    Response<Id> response;
    try {
      Id id = getItemVersionManager(context).create(context, itemId, baseVersionId, data);
      response = new Response<>(id);
      logger.info("create item version - item:" + itemId.getValue() + " version:" + id.getValue());
    } catch (ZusammenException ze) {
      ReturnCode returnCode =
          new ReturnCode(ErrorCode.ZU_ITEM_VERSION_CREATE, Module.ZDB, null, ze.getReturnCode());
      logger.error(returnCode.toString(), ze);
      response = new Response<>(ze.getReturnCode());
    } catch (Exception ex) {
      ReturnCode returnCode = new ReturnCode(ErrorCode.SYSTEM_ERROR, Module.ZDB, ex.getMessage(),
          null);
      logger.error(returnCode.toString(), ex);
      response = new Response<>(returnCode);
    }
    return response;
  }

  @Override
  public Response<Void> update(SessionContext context, Id itemId, Id versionId,
                               ItemVersionData data) {
    Response response;
    try {
      getItemVersionManager(context).update(context, itemId, versionId, data);
      response = new Response<>(Void.TYPE);
    } catch (ZusammenException ze) {
      ReturnCode returnCode =
          new ReturnCode(ErrorCode.ZU_ITEM_VERSION_UPDATE, Module.ZDB, null, ze.getReturnCode());
      logger.error(returnCode.toString(), ze);
      response = new Response<>(returnCode);
    }
    return response;
  }

  @Override
  public Response<Void> delete(SessionContext context, Id itemId, Id versionId) {

    Response response;
    try {
      getItemVersionManager(context).delete(context, itemId, versionId);
      response = new Response<>(Void.TYPE);
    } catch (ZusammenException ze) {
      ReturnCode returnCode =
          new ReturnCode(ErrorCode.ZU_ITEM_VERSION_DELETE, Module.ZDB, null, ze.getReturnCode());
      logger.error(returnCode.toString(), ze);
      response = new Response<>(returnCode);
    }
    return response;
  }

  @Override
  public Response<ItemVersionStatus> getStatus(SessionContext context, Id itemId, Id versionId) {
    Response<ItemVersionStatus> response;
    try {
      response = new Response<>(getItemVersionManager(context).getStatus(context, itemId, versionId));
    } catch (ZusammenException ze) {
      ReturnCode returnCode =
          new ReturnCode(ErrorCode.ZU_ITEM_VERSION_GET_STATUS, Module.ZDB, null, ze.getReturnCode());
      logger.error(returnCode.toString(), ze);
      response = new Response<>(returnCode);
    }
    return response;
  }

  @Override
  public Response<Void> tag(SessionContext context, Id itemId, Id versionId, Id changeId, Tag tag) {
    Response response;
    try {
      getItemVersionManager(context).tag(context, itemId, versionId, changeId, tag);
      response = new Response<>(Void.TYPE);
    } catch (ZusammenException ze) {
      ReturnCode returnCode =
          new ReturnCode(ErrorCode.ZU_ITEM_VERSION_TAG, Module.ZDB, null, ze.getReturnCode());
      logger.error(returnCode.toString(), ze);
      response = new Response<>(returnCode);
    }
    return response;
  }

  @Override
  public Response<Void> publish(SessionContext context, Id itemId, Id versionId, String message) {

    Response response;
    try {
      getItemVersionManager(context).publish(context, itemId, versionId, message);
      response = new Response<>(Void.TYPE);
    } catch (ZusammenException ze) {
      ReturnCode returnCode =
          new ReturnCode(ErrorCode.ZU_ITEM_VERSION_PUBLISH, Module.ZDB, null, ze.getReturnCode());
      logger.error(returnCode.toString(), ze);
      response = new Response<>(returnCode);
    }
    return response;
  }

  @Override
  public Response<MergeResult> sync(SessionContext context, Id itemId, Id versionId) {
    Response<MergeResult> response;
    try {

      CoreMergeResult coreMergeResult =
          getItemVersionManager(context).sync(context, itemId, versionId);
      MergeResult mergeResult = MergeResultConvertor.getMergeResult(coreMergeResult);
      response = new Response<>(mergeResult);
    } catch (ZusammenException ze) {
      ReturnCode returnCode =
          new ReturnCode(ErrorCode.ZU_ITEM_VERSION_SYNC, Module.ZDB, null, ze.getReturnCode());
      logger.error(returnCode.toString(), ze);
      response = new Response<>(returnCode);
    }
    return response;
  }

  @Override
  public Response<MergeResult> merge(SessionContext context, Id itemId, Id versionId, Id
      sourceVersionId) {

    Response<MergeResult> response;
    try {
      CoreMergeResult coreMergeResult =
          getItemVersionManager(context).merge(context, itemId, versionId, sourceVersionId);
      MergeResult mergeResult = MergeResultConvertor.getMergeResult(coreMergeResult);
      response = new Response<>(mergeResult);
    } catch (ZusammenException ze) {
      ReturnCode returnCode =
          new ReturnCode(ErrorCode.ZU_ITEM_VERSION_MERGE, Module.ZDB, null, ze.getReturnCode());
      logger.error(returnCode.toString(), ze);
      response = new Response<>(returnCode);
    }
    return response;
  }

  @Override
  public Response<ItemVersionHistory> listHistory(SessionContext context, Id itemId, Id versionId
  ) {
    Response<ItemVersionHistory> response;
    try {
      ItemVersionHistory itemVersionHistory =
          getItemVersionManager(context).listHistory(context, itemId, versionId);
      response = new Response<>(itemVersionHistory);
    } catch (ZusammenException ze) {
      ReturnCode returnCode =
          new ReturnCode(ErrorCode.ZU_ITEM_VERSION_HISTORY, Module.ZDB, null, ze.getReturnCode());
      logger.error(returnCode.toString(), ze);
      response = new Response<>(returnCode);
    }
    return response;

  }

  @Override
  public Response<Void> resetHistory(SessionContext context, Id itemId, Id versionId,
                                     String changeRef) {
    Response response;
    try {
      getItemVersionManager(context).resetHistory(context, itemId, versionId, changeRef);
      response = new Response<>(Void.TYPE);
    } catch (ZusammenException ze) {
      ReturnCode returnCode =
          new ReturnCode(ErrorCode.ZU_ITEM_VERSION_RESET_HISTORY, Module.ZDB, null,
              ze.getReturnCode());
      logger.error(returnCode.toString(), ze);
      response = new Response<>(returnCode);
    }
    return response;
  }

  @Override
  public Response<ItemVersionConflict> getConflict(SessionContext context, Id itemId,
                                                   Id versionId) {
    Response<ItemVersionConflict> response;
    try {

      ItemVersionConflict itemVersionConflict = ItemVersionConflictConvertor
          .convert(getItemVersionManager(context).getConflict(context, itemId, versionId));
      response = new Response<>(itemVersionConflict);
    } catch (ZusammenException ze) {
      ReturnCode returnCode =
          new ReturnCode(ErrorCode.ZU_ITEM_VERSION_GET_CONFLICT, Module.ZDB, null, ze.getReturnCode
              ());
      logger.error(returnCode.toString(), ze);
      response = new Response<>(returnCode);
    }
    return response;
  }

  private ItemVersionManager getItemVersionManager(SessionContext context) {
    return ItemVersionManagerFactory.getInstance().createInterface(context);
  }
}
