package pama1234.gdx.game.element;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.SortedIntList;

/**
 * bassed on DecalBatch
 */
public class Entity3DBatch implements Disposable{
  private static final int DEFAULT_SIZE=1000;
  private float[] vertices;
  private Mesh mesh;

  private final SortedIntList<Array<Entity3D>> groupList=new SortedIntList<Array<Entity3D>>();
  private Entity3DGroupStrategy groupStrategy;
  private final Pool<Array<Entity3D>> groupPool=new Pool<Array<Entity3D>>(16) {
    @Override
    protected Array<Entity3D> newObject() {
      return new Array<Entity3D>(false,100);
    }
  };
  private final Array<Array<Entity3D>> usedGroups=new Array<Array<Entity3D>>(16);

  /**
   * Creates a new Entity3DBatch using the given {@link Entity3DGroupStrategy}. The most commong strategy to
   * use is a {@link CameraEntity3DGroupStrategy}
   * 
   * @param groupStrategy
   */
  public Entity3DBatch(Entity3DGroupStrategy groupStrategy) {
    this(DEFAULT_SIZE,groupStrategy);
  }

  public Entity3DBatch(int size,Entity3DGroupStrategy groupStrategy) {
    initialize(size);
    setEntity3DGroupStrategy(groupStrategy);
  }

  /**
   * Sets the {@link Entity3DGroupStrategy} used
   * 
   * @param groupStrategy Group strategy to use
   */
  public void setEntity3DGroupStrategy(Entity3DGroupStrategy groupStrategy) {
    this.groupStrategy=groupStrategy;
  }

  /**
   * Initializes the batch with the given amount of decal objects the buffer is able to hold when
   * full.
   *
   * @param size Maximum size of decal objects to hold in memory
   */
  public void initialize(int size) {
    vertices=new float[size*Decal.SIZE];

    Mesh.VertexDataType vertexDataType=Mesh.VertexDataType.VertexArray;
    if(Gdx.gl30!=null) {
      vertexDataType=Mesh.VertexDataType.VertexBufferObjectWithVAO;
    }
    mesh=new Mesh(vertexDataType,false,size*4,size*6,
      new VertexAttribute(VertexAttributes.Usage.Position,3,ShaderProgram.POSITION_ATTRIBUTE),
      new VertexAttribute(VertexAttributes.Usage.ColorPacked,4,ShaderProgram.COLOR_ATTRIBUTE),
      new VertexAttribute(VertexAttributes.Usage.TextureCoordinates,2,ShaderProgram.TEXCOORD_ATTRIBUTE+"0"));

    short[] indices=new short[size*6];
    int v=0;
    for(int i=0;i<indices.length;i+=6,v+=4) {
      indices[i]=(short)(v);
      indices[i+1]=(short)(v+2);
      indices[i+2]=(short)(v+1);
      indices[i+3]=(short)(v+1);
      indices[i+4]=(short)(v+2);
      indices[i+5]=(short)(v+3);
    }
    mesh.setIndices(indices);
  }

  /** @return maximum amount of decal objects this buffer can hold in memory */
  public int getSize() {
    return vertices.length/Decal.SIZE;
  }

  /**
   * Add a decal to the batch, marking it for later rendering
   *
   * @param decal Entity3D to add for rendering
   */
  public void add(Entity3D decal) {
    int groupIndex=groupStrategy.decideGroup(decal);
    Array<Entity3D> targetGroup=groupList.get(groupIndex);
    if(targetGroup==null) {
      targetGroup=groupPool.obtain();
      targetGroup.clear();
      usedGroups.add(targetGroup);
      groupList.insert(groupIndex,targetGroup);
    }
    targetGroup.add(decal);
  }

  /**
   * Flush this batch sending all contained decals to GL. After flushing the batch is empty once
   * again.
   */
  public void flush() {
    render();
    clear();
  }

  /** Renders all decals to the buffer and flushes the buffer to the GL when full/done */
  protected void render() {
    groupStrategy.beforeGroups();
    for(SortedIntList.Node<Array<Entity3D>> group:groupList) {
      groupStrategy.beforeGroup(group.index,group.value);
      ShaderProgram shader=groupStrategy.getGroupShader(group.index);
      render(shader,group.value);
      groupStrategy.afterGroup(group.index);
    }
    groupStrategy.afterGroups();
  }

  /**
   * Renders a group of vertices to the buffer, flushing them to GL when done/full
   *
   * @param decals Entity3Ds to render
   */
  private void render(ShaderProgram shader,Array<Entity3D> decals) {
    // batch vertices
//    Entity3DMaterial lastMaterial=null;
//    int idx=0;
    for(Entity3D decal:decals) {
//      if(lastMaterial==null||!lastMaterial.equals(decal.getMaterial())) {
//        if(idx>0) {
//          flush(shader,idx);
//          idx=0;
//        }
//        decal.getMaterial().set();
//        lastMaterial=decal.getMaterial();
//      }
      //            decal.update();
//      System.arraycopy(decal.getVertices(),0,vertices,idx,decal.getVertices().length);
//      idx+=decal.getVertices().length;
      // if our batch is full we have to flush it
//      if(idx==vertices.length) {
//        flush(shader,idx);
//        idx=0;
//      }
    }
    // at the end if there is stuff left in the batch we render that
//    if(idx>0) {
//      flush(shader,idx);
//    }
  }

  /**
   * Flushes vertices[0,verticesPosition[ to GL verticesPosition % Entity3D.SIZE must equal 0
   *
   * @param verticesPosition Amount of elements from the vertices array to flush
   */
  protected void flush(ShaderProgram shader,int verticesPosition) {
    mesh.setVertices(vertices,0,verticesPosition);
    mesh.render(shader,GL20.GL_TRIANGLES,0,verticesPosition/4);
  }

  /** Remove all decals from batch */
  protected void clear() {
    groupList.clear();
    groupPool.freeAll(usedGroups);
    usedGroups.clear();
  }

  /**
   * Frees up memory by dropping the buffer and underlying resources. If the batch is needed again
   * after disposing it can be {@link #initialize(int) initialized} again.
   */
  public void dispose() {
    clear();
    vertices=null;
    mesh.dispose();
  }
}
