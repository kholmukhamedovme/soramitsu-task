package me.kholmukhamedov.soramitsutest.models.presentation;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class ItemModelTest {

    private static final String SOME_TITLE = "Some title";
    private static final String IMAGE_URL_WITH_OCCURRENCE = "http://some.domain.com/path_to_m.file";
    private static final String IMAGE_URL_WITHOUT_OCCURRENCE = "http://some.domain.com/path_to.file";
    private static final String IMAGE_URL_WITH_OCCURRENCE_AFTER_REPLACE = "http://some.domain.com/path_to.file";

    @Test
    public void getFullscreenImageUrl_withOccurrence() {
        ItemModel model = new ItemModel(SOME_TITLE, IMAGE_URL_WITH_OCCURRENCE);

        String actual = model.getFullscreenImageUrl();

        assertEquals(actual, IMAGE_URL_WITH_OCCURRENCE_AFTER_REPLACE);
    }

    @Test
    public void getFullscreenImageUrl_withoutOccurrence() {
        ItemModel model = new ItemModel(SOME_TITLE, IMAGE_URL_WITHOUT_OCCURRENCE);

        String actual = model.getFullscreenImageUrl();

        assertEquals(actual, IMAGE_URL_WITHOUT_OCCURRENCE);
    }

}
