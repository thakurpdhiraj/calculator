package in.dhitha.tofrompanels.time;

import in.dhitha.tofrompanels.parent.Parent;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.wicket.ajax.AjaxRequestTarget;

@Slf4j
public class Time extends Parent {
  public Time(String id) {
    super(id, "Time");
  }

  @Override
  protected void changeToField(AjaxRequestTarget ajaxRequestTarget) {
    super.changeToField(ajaxRequestTarget);
    Map<String, Double> returnMap =
        TimeUtil.convertTime(fromValue, toValue, fromSelected, toSelected);
    toValue = returnMap.get("toValue");
    ajaxRequestTarget.add(toField);
  }

  @Override
  protected void changeFromField(AjaxRequestTarget ajaxRequestTarget) {
    super.changeFromField(ajaxRequestTarget);
    Map<String, Double> returnMap =
        TimeUtil.convertTime(fromValue, toValue, fromSelected, toSelected);
    fromValue = returnMap.get("fromValue");
    ajaxRequestTarget.add(fromField);
  }

  @Override
  protected List<String> getDropDownList() {
    return TimeUtil.timeList;
  }
}
