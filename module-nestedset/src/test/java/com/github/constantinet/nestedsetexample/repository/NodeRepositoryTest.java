package com.github.constantinet.nestedsetexample.repository;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import com.github.constantinet.nestedsetexample.domain.Node;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import java.util.Collection;
import java.util.List;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-jpa-context.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    TransactionDbUnitTestExecutionListener.class})
@DbUnitConfiguration(databaseConnection = {"testDataSource"})
@Transactional
public class NodeRepositoryTest {

  @Autowired
  private NodeRepository nodeRepository;

  @Test
  @DatabaseSetup("classpath:datasets/NodeRepository-initialData.xml")
  public void testFindLeafNodes_whenNodesExist_nonEmptyCollectionReturned() {
    // when
    final Collection<Node> leafNodes = nodeRepository.findLeafNodes();

    // then
    assertThat(leafNodes, containsInAnyOrder(
        getAllOfMatcher(8L, "Convertibles", 4, 5),
        getAllOfMatcher(9L, "Minivans", 6, 7),
        getAllOfMatcher(5L, "Buses", 9, 10),
        getAllOfMatcher(6L, "Trains", 13, 14),
        getAllOfMatcher(7L, "Trams", 15, 16)
    ));
  }

  @Test
  public void testFindLeafNodes_whenNoNodesExist_emptyCollectionReturned() {
    // when
    final Collection<Node> leafNodes = nodeRepository.findLeafNodes();

    // then
    assertThat(leafNodes, empty());
  }

  @Test
  @DatabaseSetup("classpath:datasets/NodeRepository-initialData.xml")
  public void testFindPathForNode_whenLeafNodePassed_nonEmptyListReturned() {
    // given
    final Node leafNode = new Node(8L, "Convertibles", 4, 5);

    // when
    final List<Node> path = nodeRepository.findPathForNode(leafNode);

    // then
    assertThat(path, contains(
        getAllOfMatcher(1L, "Vehicles", 1, 18),
        getAllOfMatcher(2L, "Motor Vehicles", 2, 11),
        getAllOfMatcher(4L, "Cars", 3, 8)
    ));
  }

  @Test
  @DatabaseSetup("classpath:datasets/NodeRepository-initialData.xml")
  public void testFindPathForNode_whenRootNodePassed_emptyListReturned() {
    // given
    final Node rootNode = new Node(1L, "Vehicles", 1, 18);

    // when
    final List<Node> path = nodeRepository.findPathForNode(rootNode);

    // then
    assertThat(path, empty());
  }

  @Test
  @DatabaseSetup("classpath:datasets/NodeRepository-initialData.xml")
  public void testFindPathForNode_whenNonExistingNodePassed_emptyListReturned() {
    // given
    final Node nonExistingNode = new Node(10L, "Some Node");

    // when
    final List<Node> path = nodeRepository.findPathForNode(nonExistingNode);

    // then
    assertThat(path, empty());
  }

  @Test
  @DatabaseSetup("classpath:datasets/NodeRepository-initialData.xml")
  public void testFindChildNodesForNode_whenLeafNodePassed_emptyListReturned() {
    // given
    final Node leafNode = new Node(8L, "Convertibles", 4, 5);

    // when
    final List<Node> childNodes = nodeRepository.findChildNodesForNode(leafNode);

    // then
    assertThat(childNodes, empty());
  }

  @Test
  @DatabaseSetup("classpath:datasets/NodeRepository-initialData.xml")
  public void testFindChildNodesForNode_whenRootNodePassed_nonEmptyListReturned() {
    // given
    final Node rootNode = new Node(1L, "Vehicles", 1, 18);

    // when
    final List<Node> childNodes = nodeRepository.findChildNodesForNode(rootNode);

    // then
    assertThat(childNodes, contains(
        getAllOfMatcher(2L, "Motor Vehicles", 2, 11),
        getAllOfMatcher(4L, "Cars", 3, 8),
        getAllOfMatcher(8L, "Convertibles", 4, 5),
        getAllOfMatcher(9L, "Minivans", 6, 7),
        getAllOfMatcher(5L, "Buses", 9, 10),
        getAllOfMatcher(3L, "Rail Vehicles", 12, 17),
        getAllOfMatcher(6L, "Trains", 13, 14),
        getAllOfMatcher(7L, "Trams", 15, 16)
    ));
  }

  @Test
  @DatabaseSetup("classpath:datasets/NodeRepository-initialData.xml")
  public void testFindChildNodesForNode_whenNonExistingNodePassed_emptyListReturned() {
    // given
    final Node nonExistingNode = new Node(10L, "Some Node");

    // when
    final List<Node> childNodes = nodeRepository.findChildNodesForNode(nonExistingNode);

    // then
    assertThat(childNodes, empty());
  }

  @Test
  @DatabaseSetup("classpath:datasets/NodeRepository-initialData.xml")
  public void testFindNodeDepth_whenLeafNodePassed_correctDepthReturned() {
    // given
    final Node leafNode = new Node(8L, "Convertibles", 4, 5);

    // when
    final int depth = nodeRepository.findNodeDepth(leafNode);

    // then
    assertThat(depth, is(3));
  }

  @Test
  @DatabaseSetup("classpath:datasets/NodeRepository-initialData.xml")
  public void testFindNodeDepth_whenRootNodePassed_zeroDepthReturned() {
    // given
    final Node rootNode = new Node(1L, "Vehicles", 1, 18);

    // when
    final int depth = nodeRepository.findNodeDepth(rootNode);

    // then
    assertThat(depth, is(0));
  }

  @Test
  @DatabaseSetup("classpath:datasets/NodeRepository-initialData.xml")
  public void testFindNodeDepth_whenNonExistingNodePassed_zeroDepthReturned() {
    // given
    final Node nonExistingNode = new Node(10L, "Some Node");

    // when
    final int depth = nodeRepository.findNodeDepth(nonExistingNode);

    // then
    assertThat(depth, is(0));
  }

  private Matcher<Node> getAllOfMatcher(final Long id, final String name, final Integer left,
      final Integer right) {
    return allOf(
        hasProperty("id", is(id)),
        hasProperty("name", is(name)),
        hasProperty("left", is(left)),
        hasProperty("right", is(right))
    );
  }
}