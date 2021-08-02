# 1. 线程的 run() 和 start() 有什么区别？
* 每个线程都是通过特定的 Thread 对象所对应的 run() 方法来完成操作的，run() 方法称为线程体；通过调用 Thread 类的 start() 方法来启动一个线程。
* strat() 方法用来启动线程，run() 方法用于执行线程运行时的代码。run() 方法可以重复调用，start() 只能调用一次。
* start() 方法启动一个线程，真正实现了多线程运行。调用 start() 方法无须等待 run 方法体代码执行完毕，可以直接执行其他的代码，此时线程处于就绪状态，并没有运行；
然后通过此 Thread 类的 run() 方法来完成其运行状态；run() 方法运行结束，此线程终止，CPU 继续调度其他线程。
* run() 方法时存在于线程内的，只是线程里的一个函数，若直接调用 run() 则相当于直接调用了一个普通函数，而不是多线程的。直接调用 run() 方法必须等待 run() 方法体执行完毕才能执行下面的代码，故执行路径还是只有一条，不存在线程的特征。
所以在执行多线程时要使用 start() 方法而不是 run()。
  
# 2. 为什么调用 strat() 方法时会执行 run() 方法？为什么不直接调用 run() 方法？
new 一个 Thread，此时线程进入了新建状态。调用其 start() 方法，会启动一个线程并使该线程进入就绪状态，当分配到的时间片后就可以开始运行了。
运行阶段，start() 会执行线程相应的准备工作，然后自动执行 run() 方法的内容，这是真正的多线程工作。

直接执行 run() 方法，会将 run() 方法作为 main 线程下的一个普通方法去执行，并不是开启某一个线程去执行，故并不是多线程工作。

# 3. Sychronized 的原理是什么？
sychronized 又叫做内置锁，因为使用 sychronized 加锁的同步代码块在字节码引擎中执行时，实际上是通过锁对象的 monitor 的取用与释放来实现的。
monitor 内置于任何一个对象，sychronized 利用 monitor 来实现加锁解锁，因此又称为内置锁。

也正因于此，锁对象可以是任意对象：
* sychronized(lock) 加锁时，用到的是 lock 对象内置的 monitor
* 一个对象的 monitor 是唯一的，相当于一个唯一的许可证，获取许可证的线程才可以执行，执行完毕后释放对象的 monitor 后，该 monitor 才能被其他的线程获取

sychronized 加锁的同步块的执行过程：
```java
// 现在假设有代码块：  
    syncrhoized（Object lock）{
        同步代码...;
    }
// 它在字节码文件中被编译为：
    monitorenter; // 获取monitor许可证，进入同步块
    同步代码...
    monitorexit; // 离开同步块后，释放monitor许可证
```


# 4. JVM 对 Java 的原生锁进行了哪些优化？
* 自旋锁：在线程进行阻塞的时候，先让线程自旋等待一段时间，可能在这段时间里其他线程已经解锁，这时就无需再让线程进行阻塞操作了(自选的默认次数是 10)。
* 自适应自旋锁：自旋锁的升级，自旋的次数不在固定，由前一次的自旋次数和锁拥有者的状态决定。
* 锁消除：在动态编译同步代码块的时候，JIT 编译器借助逃逸分析技术来判断锁对象是否只能被一个线程访问，如果是则可以取消锁。
* 锁粗化：当 JIT 编译器发现有一系列的操作都是对同一个对象反复的加锁与解锁，此时会将加锁同步的范围粗化至整个操作系列的外部。

# 5. 为什么 wait()、notify()、notifyAll() 必须在 Object 类中定义？
Java 中，任何对象都可以作为锁，wait() 方法和 notify() 方法用于等待对象的锁或者唤醒线程；在 Java 线程中并没有可供对象使用的锁，故任意对象调用方法一定定义在 Object 类中。

有人说既然是线程放弃对象锁，那可以把 wait() 方法定义在 Thread 类中，新定义的线程继承于 Thread 类，也不需要重新定义 wait() 的实现。但是这种设计存在一个问题：
一个线程可以持有多个锁，而当线程放弃锁的时候就无法判断到底要放弃哪个锁，所以管理起来更加复杂。

# 6. Java 中如何实现多线程之间的同步协作与通信协作？
## 线程的状态以及转化图
* new：新建状态。线程创建完成时为新建状态，即 new Thread(...)，此时还没有调用 start() 方法。
* runnable：就绪状态。当调用线程的 start() 方法后，线程进入就绪状态，等待 CPU 资源。处于就绪状态的线程由 Java 运行时系统的线程调度程序(Thread scheduler)调度。
* running：运行状态。就绪状态的线程获取到 CPU 执行权后进入运行状态，开始执行 run() 方法。
* blocked：阻塞状态。线程没有执行完，但由于某种原因让出 CPU 执行权，自身进入阻塞状态。
* dead：死亡状态。线程执行完成或者执行过程中出现异常，线程进入死亡状态。

![java03-1.png](./picture/java03-1.png)

## 线程间的同步协作
线程之间的同步可以由下列方法实现： 
* sychronized 同步锁
```java
syncrhoized（Object lock）{
    需要同步的代码...;
}
```
* ReentrantLock 可重入锁
```java
Lock lock = new ReentranLock();

lock.lock();
 　 需要同步的代码；
lock.unlock();
```
* ReadWriteLock 读写锁
```java
ReentranReadWriteLock lock = new ReentranReadWriteLock();

lock.readLock().lock();
 　 需要读锁同步的代码；
lock.readLock().unlock();

lock.writeLock().lock();
 　 需要写锁同步的代码；
lock.writeLock().unlock();
```

## 线程间的通信协作
在线程获得锁而执行的过程中，执行到某一处时需要申请同一把锁的其他线程先执行，此时就需要当前线程让出同步锁以及 CPU (进入阻塞状态)，让其他的线程先获取同步锁以及 CPU 而执行。
等待其他线程执行完毕并释放同步锁之后再通知当前线程唤醒 (就绪态)，继而申请同步锁和 CPU 以继续执行。

在这个过程中线程之间的资源让出、挂起、唤醒等操作就是通过线程的通信来实现的。其主要有下列两种方式：
* sychronized 加锁线程的 `wait() / notify() / notifyAll()`
* ReentrantLock 类加锁线程的 Condition 类的 `await() / signal() / signalAll()`

# 7. Thread 类的 sleep() 和 yield() 方法有什么区别？
* 线程自身可以通过调用 sleep() 方法进入阻塞态，暂时让出 CPU 资源，但是不释放锁。休眠时间过后自动恢复就绪态，等待 CPU 资源调度执行；
* 线程自身可以通过调用 yield() 方法由运行态变为就绪态，这个过程称为让步：即正在运行的线程让出 CPU 资源给其他就绪态的线程先执行，自身则回到就绪态等待 CPU 再次调度执行；
实际过程中 yield() 无法保证实现让步的目的，因为执行让步的线程很有可能被线程调度程序再次选中！
  
# 8. 为什么说 sychronized 是非公平锁？
非公平锁是指多个线程获取锁的顺序并不是按照申请锁的顺序，当锁被释放后，任何一个线程都有机会竞争得到锁，这样做的目的是提高效率，但缺点是可能会产生线程饥饿现象。

线程饥饿是指”线程因无法访问所需资源而无法执行下去的情况“。若采用非公平锁，则优先级低的线程得到执行的机会就会很小，导致线程饥饿；持有锁的线程执行时间过长也可能导致线程饥饿。

解决饥饿的几个方法：
* 保证资源充足
* 公平分配资源
* 避免持有锁的线程长时间执行

# 9. 谈谈 volatile 有什么特点？为什么它可以保证可见性？
volatile 只能作用于变量，其保证操作的可见性和有序性，不保证原子性。

在 Java 内存模型中分为主内存和工作内存。Java 内存模型规定所有的变量存储在主内存中，每个线程都有自己的工作内存。

主内存和工作内存之间的交互可以分为 8 个原子操作：
`lock / unlock / read / load / assign / use / store / write`

volatile 修饰的变量，只有进行 assign 操作，才可以 load；只用 load，才可以 use。这样就保证了在工作内存中操作 volatile 修饰的变量都会同步到主内存中。

![java03-2.png](./picture/java03-2.jpg)

# 10. 为什么说 sychronized 是悲观锁？乐观锁的实现原理是什么？什么是 CAS，他有什么特性？
Sychronized 的并发策略是悲观的，无论是否产生竞争，任何数据的操作都必须加锁。

乐观锁的核心是 CAS，CAS 包括内存值、预估值、新值。只有当内存值等于预期值时，才会将内存值修改为新值。

# 11. 乐观锁一定就是好的么？
乐观锁认为对一个对象的操作不会引发冲突，所以每一次操作都不加锁，只是当最后提交更改时验证是否发生冲突。如果冲突则继续尝试，直到成功为止，这个过程称为自旋。

乐观锁没有加锁，但其引入了 ABA 问题，此问题一般采用版本号进行控制；如果产生自旋的次数过多，则不能提升效率，反而降低效率；乐观锁只能保证一个对象的原子性。

# 12. 请对比 Sychronized 和 ReentrantLock 的异同？
两者都是阻塞式的同步，也就是说当一个线程获得了对象锁，进入同步代码块，其他访问该同步代码块的线程都必须阻塞在同步代码块外面等候。

* Sychronized
  * Sychronized 可以定义方法和代码块。其实现了自动的加锁和释放锁。
  * Sychronized 是 Java 的关键字，是在原生语法层面的互斥，需要 JVM 实现。其使用较为便利。
  * Sychronized 会在同步块的前后分别生成 monitorenter 和 monitorexit 两个字节码指令。在执行 monitorenter 指令时，首先尝试获取锁。若锁未被锁定或当前线程已经拥有锁，则将锁的计数器 +1。
    执行相应的 monitorexit 时，计数器 -1。当计数器为 0 时释放锁。若获取锁时失败，则当前线程阻塞，直到对象锁被另一个线程释放。
  * 对于普通同步方法，锁的是当前实例对象；对于静态同步方法，锁的是当前类的 class 对象；对于同步方法快，锁的是括号里的对象。  
    
* ReentranLock
  * ReentranLock 只能定义代码块。其需要手动加锁和释放锁。
  * ReentranLock 是 java.util.concurrent 包下提供的一套互斥锁，需要 lock() 和 unlock() 方法配合 try/finally 代码块来完成。
  * 相比 Synchronized，ReentrantLock 类提供了一些高级功能：
    * 等待可中断。当持有锁的线程长期不释放锁时，正在等待的线程可以选择放弃等待，该功能通过 lock.lockInterruptibly() 实现；
    * 公平锁。多个线程等待同一个锁时，必须按照申请锁的时间顺序获得锁。Sychronized 是非公平锁，ReentranLock 默认也是非公平锁，但可以通过构造函数传参 true 设置为公平锁。但公平锁性能不好。
    * 一个锁可以绑定多个条件。ReentranLock 提供了一个 Condition 类，可以实现分组唤醒需要唤醒的线程。而 Sychronized 只能随即唤醒一个线程或唤醒全部线程。

# 13. Sychronized 和 ReentranLock 是如何实现可重入的？
可重入性：当一个线程持有锁时，其他的线程尝试获取该锁时，会被阻塞；而这个线程尝试获取自己持有的锁时，如果获取成功，则说明该锁是可重入的；反之则不可重入。

Sychronized 实现可重入：synchronized 关键字经过编译后，会在同步块的前后分别形成 monitorenter 和 monitorexit 两个字节码指令。
每个锁对象内部维护一个计数器，该计数器初始值为 0，表示任何线程都可以获取该锁并执行相应的方法。
根据虚拟机规范要求，在执行 monitorenter 指令时，首先要尝试获取对象的锁，如果这个对象没有被锁定，或者当前线程已经拥有了对象的锁，把锁的计数器+1。
相应的在执行 monitorexit 指令后锁计数器 -1，当计数器为0时，锁就被释放。如果获取对象锁失败，那当前线程就要阻塞等待，直到对象锁被另一个线程释放为止。

ReentranLock 实现可重入：ReentrantLock 使用内部类 Sync 来管理锁，所以真正的锁获取是由 Sync 的实现类控制的。
Sync 有两个实现类：NonfairSync（非公平锁）和 FairSync（公平锁）。Sync 通过继承 AQS 实现，在 AQS 中维护了一个`private volatile int state`来计算重入次数，避免频繁的持有释放操作带来的线程问题。

ReentranLock 源码分析：
```java
public class ReentrantLock implements Lock, java.io.Serializable {
    
  private final Sync sync;

  // Sync继承于AQS
  abstract static class Sync extends AbstractQueuedSynchronizer {
    //......
    final boolean nonfairTryAcquire(int acquires) {
      // 当前想要获取锁的线程  
      final Thread current = Thread.currentThread();
      // 当前锁的状态
      int c = getState();
      // state == 0 此时此刻没有线程持有锁
      if (c == 0) {
        // 通过 CAS 尝试获取锁  
        if (compareAndSetState(0, acquires)) {
          // 到这里就是获取到锁了，标记一下，告诉大家，现在是我占用了锁  
          setExclusiveOwnerThread(current);
          return true;
        }
      } else if (current == getExclusiveOwnerThread()) { // 进入该分支说明进行锁的重入
        int nextc = c + acquires;
        if (nextc < 0) // overflow
          throw new Error("Maximum lock count exceeded");
        setState(nextc);
        return true;
      }
      // 进入这里说明锁的获取失败
      return false;
    }
    //......
  }
  
  static final class NonfairSync extends Sync {
    //......
  }
  
  static final class FairSync extends Sync {
    //......
  }
  
  // ReentrantLock默认是非公平锁
  public ReentrantLock() {
    sync = new NonfairSync();
  }
  // 可以通过向构造方法中传true来实现公平锁
  public ReentrantLock(boolean fair) {
    sync = fair ? new FairSync() : new NonfairSync();
  }
  //......
}
```

当一个线程在获取锁过程中，先判断 state 的值是否为0，如果是表示没有线程持有锁，就可以尝试获取锁。当 state 的值不为0时，表示锁已经被一个线程占用了，这时会做一个判断 `current==getExclusiveOwnerThread()`，判断是看当前持有锁的线程是不是自己，如果是自己，那么将 state 的值 +1，表示重入返回即可。

# 14. 什么是锁消除和锁粗化？
* 锁消除：虚拟机根据一个对象是否真正存在同步情况，若不存在同步情况，则对该对象的访问无需经过加锁解锁的操作。锁消除的前提是 Java 必须运行在 server 模式，并且开启逃逸分析。
* 锁粗化：锁的请求、释放都会消耗一定的系统资源，高频的锁请求不利于系统性能的优化。锁粗化就是将多次的锁请求合并成一个请求，扩大锁的范围，降低锁的请求和释放带来的性能损耗。

逃逸分析：逃逸分析就是分析一个对象是否可能逃出他的作用域。如果一个对象作为参数的返回值返回，那么他在方法外部就有可能被当作一个全局对象使用，就可能会发生线程安全问题，此时就称该对象发生逃逸。
当产生逃逸时，就不能进行锁消除。但如果没有发生逃逸，锁消除就可以带来一定的性能提升。

# 15. 请谈一下 AQS 框架？

## AQS 框架



# 16. AQS 对资源的共享方式有哪些？
* Exclusive(独占)：只有一个线程可以运行，如 ReentrantLock，其又可以分为公平锁和非公平锁。
  * 公平锁：按照线程在队列中的排队顺序，先到者先拿到锁；
  * 非公平锁：当线程要获取锁时，无视队列顺序直接去抢锁，谁抢到就是谁的；
* Share(共享)：多个线程可同时执行，如 Semaphore、CountDownLatch、CyclicBarrier、ReadWriteLock。

不同的自定义同步器争用共享资源的方式也不同。自定义同步器在实现时只需要实现共享资源 state 的获取与释放方式即可，至于具体线程等待队列的维护（如获取资源失败入队/唤醒出队等），AQS 已经在顶层实现好了。

# 17. 你了解过哪些同步器，请介绍一下？
* Semaphore 
  * 通过计数器控制对共享资源的访问
  * `Semaphore(int count)`：创建拥有 count 个许可的信号量
  * `acquire()/acquire(int num)`：获取 1 个或 num 个信号量
  * `release()/release(int num)`：释放 1 个或 num 个信号量
* CountDownLatch  
  * 必须发生指定数量的事件后才能继续运行（全班所有人都离开教室后再锁门）
  * `CountDownLatch(int count)`：创建值为 count 的计数器
  * `await()`：等待计数器归零再继续执行
  * `countDown()`：计数 -1
* CyclicBarrier
  * 适用于多个线程都到达指定地点后才继续执行（四个人都到麻将馆才能开始打麻将）
  * `CyclicBarrier(int num, Runnable action)`：指定线程数量以及到达该数量后执行的动作
  * `await()`：等待全部线程到达
  
# 18. 谈一下 Java 中的线程池？
总结来说就是 3大方法、7大参数、4种策略

线程池的优势：线程复用、控制最大并发数、管理线程

## 三大方法
三大方法底层均调用 ThreadPoolExecutor 创建
```java
public class demo12_Executors {
    /**
     * Executors 工具类，三大方法
     */
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newSingleThreadExecutor(); // 创建单个线程
        ExecutorService threadPool1 = Executors.newFixedThreadPool(5); // 创建一个固定大小的线程池
        ExecutorService threadPool2 = Executors.newCachedThreadPool(); // 创建一个大小可伸缩的线程池，遇强则强遇弱则弱

        try {
            for (int i = 0; i < 100; i++) {
                threadPool.execute(()->{ // 使用线程池来创建线程
                    System.out.println(Thread.currentThread().getName() + " OK!");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown(); // 线程池使用完毕释放资源，关闭线程池
        }
    }

}
```

## 七大参数
```java
    /**
     * corePoolSize：核心线程池大小
     * maximumPoolSize：最大核心线程池大小
     * keepAliveTime：超时时间(超过这个时间没人调用就会释放)
     * unit：超时单位
     * workQueue：阻塞队列
     * threadFactory：线程工厂(创建线程使用，一般不用动)
     * defaultHandler：拒绝策略
     */
    public ThreadPoolExecutor(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                              BlockingQueue<Runnable> workQueue,
                              ThreadFactory threadFactory) {
        this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
             threadFactory, defaultHandler);
    }
```

## 四种拒绝策略
![java03-3.png](./picture/java03-3.png)

* ThreadPoolExecutor.AbortPolicy(): 直接抛出异常
* ThreadPoolExecutor.CallerRunsPolicy(): 哪来的回哪去（交由主进程处理）
* ThreadPoolExecutor.DiscardPolicy(): 直接丢掉任务，不会抛出异常
* ThreadPoolExecutor.DiscardOldestPolicy(): 尝试与最早的进行竞争，不会抛出异常


# 19. 谈一下 volatile 关键字的作用
volatile 保证可见性，禁止指令重排，不保证原子性。

* volatile 提供 happens-before 的保证，确保一个线程对变量的修改对其他线程是可见的。当一个共享变量被 volatile 修饰时，他会保证修改的值立即更新到主存；当有线程需要读取该变量时，会去主存中读取新值。
* volatile 通过在写操作的前后分别添加一层内存屏障，禁止上面的指令与下面的指令顺序交换，从而保证特定的操作的执行顺序。
* volatile 不保证原子性，对于非原子操作，在多线程的时候同样存在着线程安全问题。

```java
public class demo17_volatile02 {
    /**
     * 原子性：
     * 线程A在执行任务的时候，不能被打扰，也不能被分割。
     * 要么同时成功，要么同时失败
     * volatile 不保证原子性
     */
    private volatile static int num = 0;

    public static void add() {
        /**
         * num ++; 不是原子性操作，底层执行时分为三步
         * 1. 获得这个值
         * 2. 执行加一
         * 3. 写回这个值
         */
        num ++;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            new Thread(()->{
                for (int j = 0; j < 1000; j++) {
                    add();
                }
            }).start();
        }

        while (Thread.activeCount() > 2) Thread.yield();
        System.out.println(Thread.currentThread().getName() + "   " + num); // main   17923
    }

}
```

一般我们使用 lock 锁或 sychronized 关键字以及原子类来保证原子性。

# 20. 谈谈你对 ThreadLocal 的理解？
ThreadLocal 用于在一个线程内进行状态的传递。

很多时候，我们在线程内调用的方法需要传入参数，而方法内部又调用很多方法，同样也需要参数，这样如果全部进行传参的话就会导致某些参数传递到所有地方。
像这种在一个线程中横跨若干个方法调用，需要传递的对象，我们称为上下文 Context，它时一种状态，可以是用户身份、任务信息等等。
Java 库为我们提供了 ThreadLocal 用于在同一个线程中传递同一个对象。

实际上，可以把 ThreadLocal 看作一个全局的 `Map<Thread, Object>`，每个线程获取变量时，总是以 Thread 自身作为 key：
`Object threadLocalValue = threadLocalMap.get(Thread.currentThread());`
因此， ThreadLocal 相当于为每一个线程开辟了独立的存储空间，各个线程的 ThreadLocal 变量互不影响。

最后需要注意的是，ThreadLocal 一定要在 finally 代码块中清除。
因为当前线程执行完相关代码后，很可能会被重新放入线程池中，如果ThreadLocal没有被清除，该线程执行其他代码时，会把上一次的状态带进去。

ThreadLocal 的实例化：
`static ThreadLocal<User> threadLocalUser = new ThreadLocal<>();`

ThreadLocal 的基本使用：
```java
void processUser(user) {
    try {
        threadLocalUser.set(user);
        step1();
        step2();
    } finally {
        threadLocalUser.remove();
    }
}

void step1() {
    User u = threadLocalUser.get();
    log();
    printUser();
}

void log() {
    User u = threadLocalUser.get();
    println(u.name);
}

void step2() {
    User u = threadLocalUser.get();
    checkUser(u.id);
}
```

# 21. ThreadLocal 是怎样解决并发安全的？
Java 中常用两种机制来解决多线程的并发问题：
* sychronized 方式，通过锁机制，使一个线程在执行时，另一个线程等待，是一种以时间换空间的方式保证多线程并发安全；
* ThreadLocal 方式：通过创建线程局部变量，以空间换时间的方式保证多线程并发安全；

在 Spring 的源码中，就使用了 ThreadLocal 来管理连接。
在很多开源项目中，都经常使用 ThreadLocal 来控制多线程并发问题，因为它足够的简单，我们不需要关心是否有线程安全问题，因为变量是每个线程所特有的。

# 22. 为什么代码会重排序？
在执行程序时，为了提高性能，处理器和编译器常常会对指令进行重排序，但并不是随意重排，需要满足下列两个条件：
* 单线程环境下不能改变程序运行的结果；
* 存在数据依赖关系的不允许重排；

需要注意：重排序不会影响单线程环境的执行结果，但会破环多线程的执行语义。

# 23. 什么是自旋？
很多 Sychronized 修饰的代码块里的代码都十分简单，执行速度非常快，此时如果让等待线程都阻塞等待的话则不太值得，因为现成的阻塞涉及到用户态和内核态的切换问题。
既然 Sychronized 里面的代码执行的非常快，那就不妨让等待锁的线程不要阻塞，而是在 sychronized 边界做忙循环，即所谓的自旋。
如果多次循环后还没有获得锁，在阻塞线程，这样是一种更好的策略。

# 24. 多线程中 sychronized 锁升级的原理是什么？
sychronized 锁升级原理：在锁对象的对象头中有一个 threadid 字段。在第一次访问时 threadid 为空，JVM 让其持有偏向锁，并将 threadid 设置为其线程 id。
再次进入的时候会首先判断 threadid 是否与其线程 id 一致，若一致则可以直接使用此对象；若不一致，则升级偏向锁为轻量级锁，通过自旋循环一定次数来获取锁。
执行一定次数之后如果还没有正常获取到要使用的对象，此时就会把轻量级锁升级为重量级锁。
此过程即 suchronized 锁的升级。

锁的升级的目的：锁升级是为了减低了锁带来的性能消耗。在 Java 6 之后优化 synchronized 的实现方式，使用了偏向锁升级为轻量级锁再升级到重量级锁的方式，从而减低了锁带来的性能消耗。

# 25. sychronized 的四种锁状态
* 无锁：无锁是指没有对资源进行锁定，所有的线程都能访问并修改同一个资源，但同时只有一个线程能修改成功。
* 偏向锁：偏向锁指当一段同步代码一直被同一个线程所访问时，即不存在多个线程的竞争时，该线程在后续访问时便会自动获得锁，从而降低锁的获取带来的损耗，提高性能。
* 轻量级锁：轻量级锁指当锁是偏向锁的时候，却被另外的线程所访问，此时偏向锁就会升级成轻量级锁，其他的线程会通过自自旋的方式尝试获取锁，线程不会阻塞，从而提高性能。
* 重量级锁：重量级锁指当一个线程获取锁之后，其余所有等待获得该锁的线程都会处于阻塞状态。













