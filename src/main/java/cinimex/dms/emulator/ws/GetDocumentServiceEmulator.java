package cinimex.dms.emulator.ws;

import javax.xml.ws.Holder;

import ru.raiffeisen.services.adpdimschema.v1.*;
import ru.raiffeisen.types.dim.document.v1.CtDocument;

public class GetDocumentServiceEmulator implements AdpDIM {

  @Override
  public void createAgreement(Holder<CtDocument> document) {
    throw new RuntimeException("Method not supported!");
  }

  @Override
  public void updateRegNumber(Holder<CtDocument> document) {
    throw new RuntimeException("Method not supported!");
  }

  @Override
  public String getDocument(String docNumber) {
    // TODO Auto-generated method stub
    return null;
  }
  
}
