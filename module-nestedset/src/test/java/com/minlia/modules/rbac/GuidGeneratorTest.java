package com.minlia.modules.rbac;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Created by will on 9/6/17.
 */
public class GuidGeneratorTest {

  @Test
  public void nextId() throws Exception {

    GuidGenerator snowFlake = new GuidGenerator(1, 1);
    for (int i = 0; i < (1 << 12); i++) {
      System.out.println(snowFlake.nextId());
    }

  }

}