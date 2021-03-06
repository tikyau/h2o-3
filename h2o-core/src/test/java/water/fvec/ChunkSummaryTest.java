package water.fvec;

import hex.CreateFrame;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import water.*;
import water.api.FrameV3;
import water.api.Schema;
import water.api.TwoDimTableBase;
import water.util.*;

public class ChunkSummaryTest extends TestUtil {
  @BeforeClass() public static void setup() { stall_till_cloudsize(1); }

  @Test public void run() {
    CreateFrame cf = new CreateFrame();
    cf.seed = 1234;
    Frame f = cf.execImpl().get();
    ChunkSummary cs = FrameUtils.chunkSummary(f);
    TwoDimTable chunk_summary_table = cs.toTwoDimTableChunkTypes();
    Log.info(chunk_summary_table);
    String json = new TwoDimTableBase().fillFromImpl(chunk_summary_table).toJsonString();
    if (H2O.CLOUD.size() == 1) {
      Assert.assertEquals(json, "{\"__meta\":{\"schema_version\":-1,\"schema_name\":\"TwoDimTableBase\",\"schema_type\":\"TwoDimTable\"},\"name\":\"Chunk compression summary\",\"description\":\"\",\"columns\":[{\"__meta\":{\"schema_version\":-1,\"schema_name\":\"ColumnSpecsBase\",\"schema_type\":\"Iced\"},\"name\":\"chunk_type\",\"type\":\"string\",\"format\":\"%8s\",\"description\":\"Chunk Type\"},{\"__meta\":{\"schema_version\":-1,\"schema_name\":\"ColumnSpecsBase\",\"schema_type\":\"Iced\"},\"name\":\"chunk_name\",\"type\":\"string\",\"format\":\"%s\",\"description\":\"Chunk Name\"},{\"__meta\":{\"schema_version\":-1,\"schema_name\":\"ColumnSpecsBase\",\"schema_type\":\"Iced\"},\"name\":\"count\",\"type\":\"int\",\"format\":\"%10d\",\"description\":\"Count\"},{\"__meta\":{\"schema_version\":-1,\"schema_name\":\"ColumnSpecsBase\",\"schema_type\":\"Iced\"},\"name\":\"count_percentage\",\"type\":\"float\",\"format\":\"%10.3f %%\",\"description\":\"Count Percentage\"},{\"__meta\":{\"schema_version\":-1,\"schema_name\":\"ColumnSpecsBase\",\"schema_type\":\"Iced\"},\"name\":\"size\",\"type\":\"string\",\"format\":\"%10s\",\"description\":\"Size\"},{\"__meta\":{\"schema_version\":-1,\"schema_name\":\"ColumnSpecsBase\",\"schema_type\":\"Iced\"},\"name\":\"size_percentage\",\"type\":\"float\",\"format\":\"%10.3f %%\",\"description\":\"Size Percentage\"}],\"rowcount\":5,\"data\":[[\"CBS\",\"CXI\",\"C1\",\"C1S\",\"C8D\"],[\"Bits\",\"Sparse Integers\",\"1-Byte Integers\",\"1-Byte Fractions\",\"64-bit Reals\"],[5,4,18,18,45],[5.555556,4.4444447,20.0,20.0,50.0],[\"    1.6 KB\",\"    704  B\",\"   20.7 KB\",\"   21.0 KB\",\"  393.6 KB\"],[0.36373374,0.15709728,4.736126,4.8003926,89.94265]]}");
    }

    TwoDimTable distribution_summary_table = cs.toTwoDimTableDistribution();
    Log.info(distribution_summary_table);
    json = new TwoDimTableBase().fillFromImpl(distribution_summary_table).toJsonString();
    if (H2O.CLOUD.size() == 1) {
      Assert.assertEquals(json, "{\"__meta\":{\"schema_version\":-1,\"schema_name\":\"TwoDimTableBase\",\"schema_type\":\"TwoDimTable\"},\"name\":\"Frame distribution summary\",\"description\":\"\",\"columns\":[{\"__meta\":{\"schema_version\":-1,\"schema_name\":\"ColumnSpecsBase\",\"schema_type\":\"Iced\"},\"name\":\"\",\"type\":\"string\",\"format\":\"%s\",\"description\":\"\"},{\"__meta\":{\"schema_version\":-1,\"schema_name\":\"ColumnSpecsBase\",\"schema_type\":\"Iced\"},\"name\":\"size\",\"type\":\"string\",\"format\":\"%s\",\"description\":\"Size\"},{\"__meta\":{\"schema_version\":-1,\"schema_name\":\"ColumnSpecsBase\",\"schema_type\":\"Iced\"},\"name\":\"number_of_rows\",\"type\":\"float\",\"format\":\"%f\",\"description\":\"Number of Rows\"},{\"__meta\":{\"schema_version\":-1,\"schema_name\":\"ColumnSpecsBase\",\"schema_type\":\"Iced\"},\"name\":\"number_of_chunks_per_column\",\"type\":\"float\",\"format\":\"%f\",\"description\":\"Number of Chunks per Column\"},{\"__meta\":{\"schema_version\":-1,\"schema_name\":\"ColumnSpecsBase\",\"schema_type\":\"Iced\"},\"name\":\"number_of_chunks\",\"type\":\"float\",\"format\":\"%f\",\"description\":\"Number of Chunks\"}],\"rowcount\":6,\"data\":[[\"172.16.2.22:54323\",\"mean\",\"min\",\"max\",\"stddev\",\"total\"],[\"  437.6 KB\",\"  437.6 KB\",\"  437.6 KB\",\"  437.6 KB\",\"      0  B\",\"  437.6 KB\"],[10000.0,10000.0,10000.0,10000.0,0.0,10000.0],[9.0,9.0,9.0,9.0,0.0,9.0],[90.0,90.0,90.0,90.0,0.0,90.0]]}");
    }

    f.remove();
  }

}

