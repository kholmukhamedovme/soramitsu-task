package me.kholmukhamedov.soramitsutest.models.converter;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.kholmukhamedov.soramitsutest.models.data.FeedBean;
import me.kholmukhamedov.soramitsutest.models.domain.Item;

import static junit.framework.Assert.assertEquals;

public class DataToDomainConverterTest {

    private DataToDomainConverter mConverter;

    @Before
    public void setUp() {
        mConverter = new DataToDomainConverter();
    }

    @Test
    public void convert() {
        for (int i = 0; i < getFrom().getItems().size(); i++) {
            Item actual = mConverter.convert(getFrom().getItems().get(i));

            assertEquals(actual, getExpected().get(i));
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Test(expected = NullPointerException.class)
    public void convert_null() {
        mConverter.convert(null);
    }

    @Test
    public void convertList() {
        List<Item> actual = mConverter.convertList(getFrom().getItems());

        assertEquals(actual, getExpected());
    }

    @SuppressWarnings("ConstantConditions")
    @Test(expected = NullPointerException.class)
    public void convertList_null() {
        mConverter.convertList(null);
    }

    @Test
    public void convertList_empty() {
        List<Item> actual = mConverter.convertList(Collections.emptyList());

        assertEquals(actual, Collections.emptyList());
    }

    private FeedBean getFrom() {
        List<FeedBean.Item> itemList = new ArrayList<>();

        itemList.add(new FeedBean.Item(
                "IMG_20180803_193703570",
                "https://www.flickr.com/photos/ltpl/29031075737/",
                new FeedBean.Item.Media(
                        "https://farm1.staticflickr.com/939/29031075737_33d2ab4b90_m.jpg"
                ),
                "2018-08-03T19:37:03-08:00",
                " <p><a href=\"https://www.flickr.com/people/ltpl/\">Lyon Township Public Library</a> posted a photo:</p> <p><a href=\"https://www.flickr.com/photos/ltpl/29031075737/\" title=\"IMG_20180803_193703570\"><img src=\"https://farm1.staticflickr.com/939/29031075737_33d2ab4b90_m.jpg\" width=\"240\" height=\"180\" alt=\"IMG_20180803_193703570\" /></a></p> ",
                "2018-08-10T21:39:12Z",
                "nobody@flickr.com (\"Lyon Township Public Library\")",
                "40567211@N06",
                ""
        ));
        itemList.add(new FeedBean.Item(
                "Techniker Beachtour Zinnowitz",
                "https://www.flickr.com/photos/tt-karl/29031077737/",
                new FeedBean.Item.Media(
                        "https://farm2.staticflickr.com/1815/29031077737_4f3ae85cbd_m.jpg"
                ),
                "2018-08-10T15:32:59-08:00",
                " <p><a href=\"https://www.flickr.com/people/tt-karl/\">tt-karl</a> posted a photo:</p> <p><a href=\"https://www.flickr.com/photos/tt-karl/29031077737/\" title=\"Techniker Beachtour Zinnowitz\"><img src=\"https://farm2.staticflickr.com/1815/29031077737_4f3ae85cbd_m.jpg\" width=\"240\" height=\"160\" alt=\"Techniker Beachtour Zinnowitz\" /></a></p> <p>Qualifikationsspiele am Freitag</p>",
                "2018-08-10T21:39:21Z",
                "nobody@flickr.com (\"tt-karl\")",
                "149521488@N08",
                ""
        ));

        return new FeedBean(
                "Uploads from everyone",
                "https://www.flickr.com/photos/",
                "",
                "2018-08-10T21:39:12Z",
                "https://www.flickr.com",
                itemList
        );
    }

    private List<Item> getExpected() {
        List<Item> expectedItemList = new ArrayList<>();

        expectedItemList.add(new Item(
                "IMG_20180803_193703570",
                "https://farm1.staticflickr.com/939/29031075737_33d2ab4b90_m.jpg"
        ));
        expectedItemList.add(new Item(
                "Techniker Beachtour Zinnowitz",
                "https://farm2.staticflickr.com/1815/29031077737_4f3ae85cbd_m.jpg"
        ));

        return expectedItemList;
    }

}
