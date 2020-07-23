package in.dhitha.error;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.http.WebResponse;

public class InternalServer extends WebPage {
  public InternalServer(){

  }

  @Override
  protected void configureResponse(WebResponse response) {
    super.configureResponse(response);
    response.setStatus(500);
  }

  @Override
  public boolean isVersioned() {
    return false;
  }

  @Override
  public boolean isErrorPage() {
    return true;
  }
}
