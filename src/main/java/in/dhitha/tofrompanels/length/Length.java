package in.dhitha.tofrompanels.length;

import in.dhitha.tofrompanels.parent.Parent;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.wicket.ajax.AjaxRequestTarget;

@Slf4j
public class Length extends Parent {

  public Length(String id) {
    super(id, "Length");
  }

  @Override
  protected void changeToField(AjaxRequestTarget ajaxRequestTarget) {
    super.changeToField(ajaxRequestTarget);
    Map<String, Double> returnMap =
        LengthUtil.convertLength(fromValue, toValue, fromSelected, toSelected);
    toValue = returnMap.get("toValue");
    ajaxRequestTarget.add(toField);
  }

  @Override
  protected void changeFromField(AjaxRequestTarget ajaxRequestTarget) {
    super.changeFromField(ajaxRequestTarget);
    Map<String, Double> returnMap =
        LengthUtil.convertLength(fromValue, toValue, fromSelected, toSelected);
    fromValue = returnMap.get("fromValue");
    ajaxRequestTarget.add(fromField);
  }

  @Override
  protected List<String> getDropDownList() {
    return LengthUtil.lengthList;
  }
}
