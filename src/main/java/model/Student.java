/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package model;

// line 10 "../main.ump"
public class Student extends Person
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Student Associations
  private Mentor mentor;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Student(String aName)
  {
    super(aName);
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Mentor getMentor()
  {
    return mentor;
  }

  public boolean hasMentor()
  {
    boolean has = mentor != null;
    return has;
  }
  /* Code from template association_SetOptionalOneToMany */
  public boolean setMentor(Mentor aMentor)
  {
    boolean wasSet = false;
    Mentor existingMentor = mentor;
    mentor = aMentor;
    if (existingMentor != null && !existingMentor.equals(aMentor))
    {
      existingMentor.removeStudent(this);
    }
    if (aMentor != null)
    {
      aMentor.addStudent(this);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    if (mentor != null)
    {
      Mentor placeholderMentor = mentor;
      this.mentor = null;
      placeholderMentor.removeStudent(this);
    }
    super.delete();
  }

}