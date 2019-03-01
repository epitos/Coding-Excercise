package exercise.coding.codiingexercisejpmc;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import exercise.coding.codiingexercisejpmc.model.Album;
import exercise.coding.codiingexercisejpmc.network.Requests;
import exercise.coding.codiingexercisejpmc.utils.ApiUtils;
import retrofit2.Callback;

@Config(constants = BuildConfig.class, sdk = 21, manifest = "app/src/main/AndroidManifest.xml")
@RunWith(RobolectricGradleTestRunner.class)
public class EndpointCallTest {

    @Mock
    private Requests mockApi;

    @Captor
    private ArgumentCaptor<Callback<ArrayList<Album>>> cb;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockApi = ApiUtils.getRequests();
    }

    @Test
    public void testAlbumEndpoint() {
        Mockito.verify(mockApi).getAlbums();
    }
}
