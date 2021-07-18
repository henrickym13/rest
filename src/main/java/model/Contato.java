package model;

import javax.persistence.*;


@Entity
@Table(name= "contato")
public class Contato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "nome")
    private String nome;

    @Column(name= "idade")
    private int idade;

    @Column(name= "email")
    private String email;

    public Contato(){

    }

    public Contato(String nome, int idade, String email){
        this.nome = nome;
        this.idade = idade;
        this.email = email;
    }

    public long getId(){
        return id;
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public int getIdade(){
        return idade;
    }

    public void setIdade(int idade){
        this.idade = idade;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }
}
