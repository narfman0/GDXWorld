/** 
 *  Taken from https://bitbucket.org/dermetfan/libgdx-utils
 *  
 *  Copyright 2014 Robin Stumm (serverkorken@gmail.com, http://dermetfan.net)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License. */

package com.blastedstudios.gdxworld.util.ui;

/** a generic accessor
 *  @author dermetfan */
public interface Accessor<T, O> {

	/** @param object the {@code O} to access
	 *  @return the accessed {@code T} from {@code O} */
	public T access(O object);

}