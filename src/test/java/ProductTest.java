import com.github.database.rider.cdi.api.DBRider;
import com.github.database.rider.core.api.dataset.DataSet;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;

import org.junit.jupiter.api.Test;

@DBRider
@QuarkusTest
//@QuarkusTestResource(DatabaseLifecycle.class)
class ProductTest {

  @Test
//  @DataSet("products1.yml")
  void testUm() {
  }

}
