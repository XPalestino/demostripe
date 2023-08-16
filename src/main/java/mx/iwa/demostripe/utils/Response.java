package mx.iwa.demostripe.utils;

public class Response {

  private boolean status;
  private String details;

  public Response() {
    super();
    this.status = true;
  }

  public Response(final boolean status, final String details) {
    super();
    this.status = status;
    this.details = details;
  }

  public boolean isStatus() {
    return status;
  }

  public void setStatus(final boolean status) {
    this.status = status;
  }

  public String getDetails() {
    return details;
  }

  public void setDetails(final String details) {
    this.details = details;
  }

  @Override
  public String toString() {
    return "Response [status=" + status + ", details=" + details + "]";
  }
}
