package com.booleansystems.codechallenge.ui.home.viewmodel;

import com.booleansystems.data.common.IBaseResultListener;
import com.booleansystems.data.search.SearchGalleryRepository;
import com.booleansystems.domain.common.BaseResponse;
import com.booleansystems.usecase.search.SearchGalleryUseCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;

/**
 * Created by oscar on 05/05/19
 * operez@na-at.com.mx
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class SearchGalleryViewModelTestJava {

    @Mock
    SearchGalleryRepository.SearchGalleryDataSource mMockDataSource;

    private SearchGalleryRepository mSearchGalleryRepository;
    private SearchGalleryUseCase mSearchGalleryUseCase;
    private SearchGalleryViewModel mViewModel;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mSearchGalleryRepository = Mockito.spy(new SearchGalleryRepository(mMockDataSource));
        mSearchGalleryUseCase = Mockito.spy(new SearchGalleryUseCase(mSearchGalleryRepository));
        mViewModel = Mockito.spy(new SearchGalleryViewModel(mSearchGalleryUseCase));
        mockSignUpServiceResponseSuccess();
    }

    private void mockSignUpServiceResponseSuccess() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                IBaseResultListener response = invocation.getArgument(2);
                response.onSuccess(new BaseResponse<>(Collections.emptyList(), true, 200));
                return response;
            }
        }).when(mMockDataSource).executeSearch(anyInt(), anyString(), any(IBaseResultListener.class));
    }

    @Test
    public void testIsConnectionValid() {
        mViewModel.startSearchGallery(true, false, "test");
        verify(mViewModel).validateSearch(false, "test");
    }

    @Test
    public void testIsConnectionNotValid() {
        mViewModel.startSearchGallery(false, false, "test");
        verify(mViewModel).sendEventNotInternetAvailable();
    }

    @Test
    public void testIsQueryEmpty() {
        mViewModel.startSearchGallery(true, false, "");
        verify(mViewModel).sendEventEmptyQuery();
    }


    @Test
    public void testIsQueryNotEmpty() {
        mViewModel.startSearchGallery(true, false, "test");
        verify(mViewModel).validateSearch(false, "test");
    }
}
