//   Copyright 2012,2015 Vaughn Vernon
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.

package base

import akka.actor._

class CompletableApp(val steps:Int) extends App {
  val canComplete = new java.util.concurrent.CountDownLatch(1);
  val canStart = new java.util.concurrent.CountDownLatch(1);
  val completion = new java.util.concurrent.CountDownLatch(steps);

  val system = ActorSystem("eaipatterns")

  def awaitCanCompleteNow = canComplete.await

  def awaitCanStartNow = canStart.await

  def awaitCompletion = {
    completion.await
    system.shutdown()
  }

  def canCompleteNow() = canComplete.countDown()

  def canStartNow() = canStart.countDown()

  def completeAll() = {
    while (completion.getCount > 0) {
      completion.countDown()
    }
  }

  def completedStep() = completion.countDown()
}