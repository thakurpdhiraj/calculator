package in.dhitha.tofrompanels.volume;

import in.dhitha.tofrompanels.parent.Parent;
import java.util.List;
import java.util.Map;
import org.apache.wicket.ajax.AjaxRequestTarget;

public class Volume extends Parent {

  public Volume(String id) {
    super(id, "Volume");
  }

  @Override
  protected void changeToField(AjaxRequestTarget ajaxRequestTarget) {
    super.changeToField(ajaxRequestTarget);
    Map<String, Double> returnMap =
        VolumeUtil.convertVolume(fromValue, toValue, fromSelected, toSelected);
    toValue = returnMap.get("toValue");
    ajaxRequestTarget.add(toField);
  }

  @Override
  protected void changeFromField(AjaxRequestTarget ajaxRequestTarget) {
    super.changeFromField(ajaxRequestTarget);
    Map<String, Double> returnMap =
        VolumeUtil.convertVolume(fromValue, toValue, fromSelected, toSelected);
    fromValue = returnMap.get("fromValue");
    ajaxRequestTarget.add(fromField);
  }

  @Override
  protected List<String> getDropDownList() {
    return VolumeUtil.volumeList;
  }
}
