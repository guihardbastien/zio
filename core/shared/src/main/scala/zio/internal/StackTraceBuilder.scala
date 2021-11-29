/*
 * Copyright 2021 John A. De Goes and the ZIO Contributors
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

package zio.internal

import zio._

class StackTraceBuilder private () { self =>
  private var last: ZTraceElement                  = null.asInstanceOf[ZTraceElement]
  private val builder: ChunkBuilder[ZTraceElement] = ChunkBuilder.make()

  def +=(trace: ZTraceElement): Unit =
    if ((trace ne last) && (trace ne null) && (trace ne ZTraceElement.empty)) {
      builder += trace
      last = trace
    }

  def result(): Chunk[ZTraceElement] = builder.result()
}
object StackTraceBuilder {
  def unsafeMake(): StackTraceBuilder = new StackTraceBuilder()
}