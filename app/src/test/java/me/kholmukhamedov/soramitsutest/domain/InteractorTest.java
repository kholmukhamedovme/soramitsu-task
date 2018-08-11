package me.kholmukhamedov.soramitsutest.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class InteractorTest {

    private static final String SOME_TAG = "dogs";

    private Interactor mInteractor;

    @Mock
    private Repository mRepository;

    @Before
    public void setUp() {
        mInteractor = new Interactor(mRepository);
    }

    @Test
    public void requestItems() {
        mInteractor.requestItems();

        verify(mRepository).getItems();
    }

    @Test
    public void requestItemsByTag() {
        mInteractor.requestItems(SOME_TAG);

        verify(mRepository).getItems(SOME_TAG);
    }

}
