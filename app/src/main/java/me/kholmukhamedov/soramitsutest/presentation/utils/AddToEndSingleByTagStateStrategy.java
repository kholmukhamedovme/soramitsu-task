package me.kholmukhamedov.soramitsutest.presentation.utils;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.ViewCommand;
import com.arellomobile.mvp.viewstate.strategy.StateStrategy;

import java.util.Iterator;
import java.util.List;

/**
 * Moxy custom strategy
 * Same as {@link com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy} but with a sign of tag
 *
 * @see <a href="https://gist.github.com/senneco/98a532df9915901da8065448722cef9f">Source</a>
 */
public final class AddToEndSingleByTagStateStrategy implements StateStrategy {

    @Override
    public <View extends MvpView> void beforeApply(List<ViewCommand<View>> currentState, ViewCommand<View> incomingCommand) {
        Iterator<ViewCommand<View>> iterator = currentState.iterator();

        while (iterator.hasNext()) {
            ViewCommand<View> entry = iterator.next();

            if (entry.getTag().equals(incomingCommand.getTag())) {
                iterator.remove();
                break;
            }
        }

        currentState.add(incomingCommand);
    }

    @Override
    public <View extends MvpView> void afterApply(List<ViewCommand<View>> currentState, ViewCommand<View> incomingCommand) {
        // pass
    }

}
