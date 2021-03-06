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

package com.amdocs.zusammen.datatypes.response;

public class Response<T> {

  private T value;
  private ReturnCode returnCode;

  public Response(T value) {
    this.value = value;
  }

  public Response(ReturnCode returnCode) {
    this.returnCode = returnCode;
  }

  public boolean isSuccessful() {
    return (returnCode == null);
  }

  public T getValue() {
    return value;
  }

  public void setValue(T value) {
    this.value = value;
  }

  public ReturnCode getReturnCode() {
    return returnCode;
  }

  public void setReturnCode(ReturnCode returnCode) {
    this.returnCode = returnCode;
  }
}
