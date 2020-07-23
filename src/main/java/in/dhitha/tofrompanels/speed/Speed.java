package in.dhitha.tofrompanels.speed;

import in.dhitha.tofrompanels.parent.Parent;
import java.util.List;
import java.util.Map;
import org.apache.wicket.ajax.AjaxRequestTarget;

public class Speed extends Parent {

  public Speed(String id) {
    super(id, "Speed");
  }

  @Override
  protected void changeToField(AjaxRequestTarget ajaxRequestTarget) {
    super.changeToField(ajaxRequestTarget);
    Map<String, Double> returnMap =
        SpeedUtil.convertSpeed(fromValue, toValue, fromSelected, toSelected);
    toValue = returnMap.get("toValue");
    ajaxRequestTarget.add(toField);
  }

  @Override
  protected void changeFromField(AjaxRequestTarget ajaxRequestTarget) {
    super.changeFromField(ajaxRequestTarget);
    Map<String, Double> returnMap =
        SpeedUtil.convertSpeed(fromValue, toValue, fromSelected, toSelected);
    fromValue = returnMap.get("fromValue");
    ajaxRequestTarget.add(fromField);
  }

  @Override
  protected List<String> getDropDownList() {
    return SpeedUtil.speedList;
  }
}
