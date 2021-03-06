package xyz.marcb.rxadapter

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import rx.Observable
import rx.observers.TestSubscriber
import kotlin.test.expect

class OptionalItemTests {

    @Mock private lateinit var viewHolder: HeaderViewHolder
    private val snapshotSubscriber = TestSubscriber<AdapterPartSnapshot>()
    private lateinit var item: OptionalItem<String, HeaderViewHolder>

    @Before fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    private fun createWith(observable: Observable<String?>) {
        item = OptionalItem(HeaderViewHolder::class.java, observable).apply {
            binder = { item -> bind(item) }
        }
        item.snapshots.subscribe(snapshotSubscriber)
    }

    @Test fun itemCountIsOneIfPresent() {
        createWith(Observable.just("A"))
        expect(1) { snapshotSubscriber.onNextEvents[0].itemCount }
    }

    @Test fun itemCountIsZeroIfNotPresent() {
        createWith(Observable.just(null))
        expect(0) { snapshotSubscriber.onNextEvents[0].itemCount }
    }

    @Test fun viewHolderClass() {
        createWith(Observable.just("A"))
        val snapshot = snapshotSubscriber.onNextEvents[0]
        expect(HeaderViewHolder::class.java) { snapshot.viewHolderClass(0) }
    }

    @Test fun binderIsInvoked() {
        createWith(Observable.just("A"))
        val snapshot = snapshotSubscriber.onNextEvents[0]
        snapshot.bind(viewHolder, index = 0)
        Mockito.verify(viewHolder).bind("A")
    }
}