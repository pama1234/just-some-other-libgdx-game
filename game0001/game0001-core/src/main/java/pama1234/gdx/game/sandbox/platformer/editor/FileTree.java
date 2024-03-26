package pama1234.gdx.game.sandbox.platformer.editor;

import java.util.LinkedList;

import com.badlogic.gdx.files.FileHandle;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.ui.element.TextButtonCam;
import pama1234.gdx.util.listener.EntityListener;
import pama1234.gdx.util.wrapper.EntityCenter;
import pama1234.gdx.util.wrapper.EntityWrapper;

/**
 * 文件管理器
 */
public class FileTree extends EntityCenter<Screen0011,EntityListener>{
  /** 根目录节点 */
  public FileHandle rootFolder;
  /** 按钮列表 */
  public EntityCenter<Screen0011,FileButton> buttons;
  /** 回收的对象池 */
  public LinkedList<FileButton> pool;

  public FileTree(Screen0011 p,FileHandle rootFolder) {
    super(p);
    this.rootFolder=rootFolder;
    buttons=new EntityCenter<>(p);
    var root=createButton(p,rootFolder,null);
    buttons.add(root);
    root.content.rectAuto(()->0,()->0);
    add(buttons);
  }

  public static FileButton createButton(Screen0011 p,FileHandle file,FileButton parentButton) {

    FileButton fileButton=new FileButton(p,null,file);
    fileButton.parent=parentButton;
    TextButtonCam<Screen0011> button=new TextButtonCam<>(p)
      .allTextButtonEvent(self-> {},self-> {
        fileButton.pressed();
      },self-> {})
      .textSupplierWithUpdate(self->self.text=file.name());
    fileButton.content=button;
    return fileButton;
  }
  public static class FileButton extends EntityWrapper<Screen0011,TextButtonCam<Screen0011>>{
    public FileTree tree;
    public FileHandle file;

    public boolean expand;
    public boolean needInit=true;
    /** 父目录 */
    public FileButton parent;
    /** 与自己相邻的文件或目录 */
    public FileButton previous,next;
    /** 子目录或文件 */
    public LinkedList<FileButton> child=new LinkedList<>();

    /** 在整个文件树中的位置 */
    public int x,y;
    /** 在目录中的位置 */
    public int pos;
    public FileButton(Screen0011 p,FileTree tree,FileHandle file) {
      super(p,null);
      this.tree=tree;
      this.file=file;
    }

    public void pressed() {
      if(file.isDirectory()) {
        if(!this.expand) {
          if(this.needInit) {

            this.needInit=false;
            var list=file.list();
            FileButton pb=this;
            for(int i=0;i<list.length;i++) {
              int j=i;
              FileButton pbs=pb;

              // 创建新按钮时,基于最后y坐标设置y坐标
              //                FileButton e=createButton(list[i],this,()->x.get()+20,()->this.content.rect.y()+(pbs==null||!pbs.expand?0:pbs.child.size()*20)+j*20+20);
              FileButton child=createButton(p,list[i],this);
              child.pos=i;
              child.x=this.x+20;
              child.y=this.y+20;
              child.content.rectAuto(()->this.x+20,()->this.y+j*20+20);
              child.previous=pbs;
              pbs.next=child;
              pb=child;
              this.child.add(child);
              tree.buttons.add(child);
            }
          }else {
            tree.buttons.add.addAll(this.child);
          }

          if(parent!=null) {
            var itr=parent.child.iterator();
            for(int i=0;i<=this.pos+1;i++) {
              itr.next();
            }
            //              while(itr.hasNext()) {
            //                var e=itr.next();
            //                e.y+=this.child.size()*20;
            //              }
            var e=itr.next();
            while(e.next!=null) {
              e.y+=this.child.size()*20;
              e=e.next;
            }
          }
        }else {
          tree.buttons.remove.addAll(this.child);

          if(parent!=null) {
            var itr=parent.child.iterator();
            for(int i=0;i<=this.pos+1;i++) {
              itr.next();
            }
            //              while(itr.hasNext()) {
            //                var e=itr.next();
            //                e.y-=this.child.size()*20;
            //              }
            var e=itr.next();
            while(e.next!=null) {
              e.y-=this.child.size()*20;
              e=e.next;
            }
          }
        }
        this.expand=!this.expand;
      }else {
        //          if(file.exists()) {}
      }
    }
  }
}
