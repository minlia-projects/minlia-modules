package com.github.constantinet.nestedsetexample.repository;

import com.github.constantinet.nestedsetexample.domain.Node;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface NodeRepository extends Repository<Node, Long> {

  /**
   * Retrieves all nodes with no child nodes (i.e. leaf nodes).
   *
   * @return {@code Collection} of {@code Node} objects
   */
  @Query("SELECT n FROM Node n WHERE n.right = n.left + 1")
  Collection<Node> findLeafNodes();

  /**
   * Retrieves the path for passed node.
   * <br/>
   * Path is a {@code List} object where first element is the root node
   * and the last element is the immediate parent of passed node.
   *
   * @param node a node to retrieve a path for
   * @return list of nodes or empty list if passed node was the root node or was not found
   */
  @Query("SELECT parent FROM Node node, Node parent " +
      "WHERE node.left BETWEEN parent.left AND parent.right " +
      "AND parent != ?1 " +
      "AND node = ?1 ORDER BY node.left")
  List<Node> findPathForNode(Node node);

  /**
   * Retrieves a list that represents a flattened tree of child nodes for passed node.
   * <br/>
   * The first element in the returned {@code List} object is the left-most child of passed node
   * followed by the left-most child of immediate descendant, then followed by the second child
   * of passed node if there are no more child nodes of it's left-most child and so on.
   *
   * @param node a node to retrieve child nodes for
   * @return list of nodes or empty list if passed node was the root node or was not found
   */
  @Query("SELECT child FROM Node node, Node child " +
      "WHERE (child.left BETWEEN node.left AND node.right) " +
      "AND (child.right BETWEEN node.left AND node.right) " +
      "AND child != ?1 " +
      "AND node = ?1 ORDER BY child.left")
  List<Node> findChildNodesForNode(Node node);

  /**
   * Retrieves a depth of a node.
   *
   * @param node a node to retrieve depth for
   * @return depth of a node in the range of 0...n
   */
  @Query("SELECT count(parent) FROM Node node, Node parent " +
      "WHERE node.left BETWEEN parent.left AND parent.right " +
      "AND parent != ?1 AND node = ?1")
  int findNodeDepth(Node node);
}