package control;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import model.Contato;
import repository.ContatoRepository;


@RestController
@RequestMapping("/api")
public class ContatoController {

    @Autowired
    ContatoRepository contatoRepository;

    @GetMapping("/contato")
    public ResponseEntity<List<Contato>> getAllContatos(@RequestParam(required = false) String nome){
        try{
            List<Contato> contatos = new ArrayList<Contato>();

            if (nome == null)
                contatoRepository.findAll().forEach(contatos::add);
            else
                contatoRepository.findByNomeContaining(nome).forEach(contatos::add);

            if (contatos.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return  new ResponseEntity<>(contatos, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/contato/{id}")
    public ResponseEntity<Contato> getContatoById(@PathVariable("id") long id){
        Optional<Contato> contatoData = contatoRepository.findById(id);

        if (contatoData.isPresent()){
            return new ResponseEntity<>(contatoData.get(), HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/contact")
    public ResponseEntity<Contato> createContato(@RequestBody Contato contato){
        try{
            Contato _contato = contatoRepository
                    .save(new Contato(contato.getNome(), contato.getIdade(), contato.getEmail()));
            return new ResponseEntity<>(_contato, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/contato/{id}")
    public ResponseEntity<Contato> updateContato(@PathVariable("id") long id, @RequestBody Contato contato){
        Optional<Contato> contatoData = contatoRepository.findById(id);

        if (contatoData.isPresent()){
            Contato _contato = contatoData.get();
            _contato.setNome(contato.getNome());
            _contato.setIdade(contato.getIdade());
            _contato.setEmail(contato.getEmail());
            return new ResponseEntity<>(contatoRepository.save(_contato), HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/contato/{id}")
    public ResponseEntity<HttpStatus>deleteContato(@PathVariable("id") long id){
        try{
            contatoRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/contato")
    public ResponseEntity<HttpStatus> deleteAllContato(){
        try{
            contatoRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
