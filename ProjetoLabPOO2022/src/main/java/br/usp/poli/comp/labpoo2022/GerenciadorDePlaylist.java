package br.usp.poli.comp.labpoo2022;

import java.io.IOException;

import org.apache.hc.core5.http.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.special.SnapshotResult;
import se.michaelthelin.spotify.model_objects.specification.Playlist;
import se.michaelthelin.spotify.requests.data.playlists.AddItemsToPlaylistRequest;
import se.michaelthelin.spotify.requests.data.playlists.CreatePlaylistRequest;

@RestController
@RequestMapping("/playlist")
public class GerenciadorDePlaylist {
	
	//Cria uma playlist vazia
	@GetMapping(value = "criador-de-playlist")
	public static void criaPlaylist()
	{
		String nomeDaPlaylist = "placeholder"; //Será modificado a pelo usuário
		final GerenciadorDoUsuarioAtual usuarioAtual = new GerenciadorDoUsuarioAtual();
	
		final CreatePlaylistRequest requisicaoDePlaylist = ControladorDeAutorizacao.getSpotifyApi().createPlaylist(usuarioAtual.getIdDeUsuario(), nomeDaPlaylist)
			.build();
		
		try {
			final Playlist playlist = requisicaoDePlaylist.execute();
			
			System.out.println("Name: " + playlist.getName());
		} catch (IOException | SpotifyWebApiException | ParseException e) {
			System.out.println("Error: "  + e.getMessage());
		}
	}
	
	@GetMapping(value = "adicona-itens")
	public static void adicionaItens () {
		String playlistSelecionada = "playlistURI"; // Deve ser substituido pelo uri correto
		String[] uris = null; //URIs das musicas a serem adicionadas (1 ou mais)
		// uris pode ser transformado em um JSON array (se for mais simples a transferencia do front-end)
		
		final AddItemsToPlaylistRequest requisicaoAdicaoItens = ControladorDeAutorizacao.getSpotifyApi()
				.addItemsToPlaylist(playlistSelecionada, uris)
				.build(); //Omite a posicao
		
		try {
			final SnapshotResult snapshotResult = requisicaoAdicaoItens.execute();
			
			System.out.println("ID do snapshot: " + snapshotResult.getSnapshotId());
		} catch (IOException |SpotifyWebApiException | ParseException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
}
