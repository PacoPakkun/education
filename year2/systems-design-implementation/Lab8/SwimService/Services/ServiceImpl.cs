using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Grpc.Core;
using Microsoft.Extensions.Logging;
using SwimServer.Repository.Interface;

namespace SwimService
{
    public class ServiceImpl : SwimService.SwimServiceBase
    {
        private readonly ILogger<ServiceImpl> _logger;

        public ServiceImpl(ILogger<ServiceImpl> logger)
        {
            _logger = logger;
        }

        private ParticipantRepository participantRepository;
        private ProbaRepository probaRepository;
        private InscriereRepository inscriereRepository;

        private UserRepository userRepository;
        private readonly IDictionary<String, IServerStreamWriter<SwimResponse>> loggedUsers;

        public ServiceImpl(ParticipantRepository repo1, ProbaRepository repo2, InscriereRepository repo3,
            UserRepository repo4)
        {
            participantRepository = repo1;
            probaRepository = repo2;
            inscriereRepository = repo3;
            userRepository = repo4;
            loggedUsers = new Dictionary<String, IServerStreamWriter<SwimResponse>>();
        }

        public override async Task login(SwimRequest request, IServerStreamWriter<SwimResponse> responseStream, ServerCallContext context)
        {
            Console.WriteLine("login request..");
            SwimModel.Domain.User usr = null;
            SwimModel.Domain.User user = new SwimModel.Domain.User(request.User.Username, request.User.Password);
            foreach (var u in userRepository.FindAll())
            {
                if (u.username == user.username && u.password == user.password)
                {
                    usr = u;
                }
            }
            if (usr != null)
                loggedUsers[user.username] = responseStream;
            else
                throw new Exception("Authentication failed.");
            
            while (!context.CancellationToken.IsCancellationRequested)
            {
                await Task.Delay(100);
            }
        }

        public override Task<SwimResponse> logout(SwimRequest request, ServerCallContext context)
        {
            Console.WriteLine("logout request..");
            loggedUsers.Remove(request.User.Username);
            return Task.FromResult(new SwimResponse() { });
        }

        public override Task<SwimResponse> findAllProbe(Null request, ServerCallContext context)
        {
            Console.WriteLine("find all probe request..");
            SwimResponse response = new SwimResponse();
            var probe = probaRepository.FindAll();
            foreach (var proba in probe)
            {
                Proba p = new Proba();
                p.Id = proba.id;
                p.Distanta = proba.distanta;
                p.Stil = proba.stil;
                response.Probe.Add(p);
            }

            return Task.FromResult(response);
        }

        public override Task<SwimResponse> addParticipant(SwimRequest request, ServerCallContext context)
        {
            Console.WriteLine("add participant request..");
            var participant = participantRepository.Add(new SwimModel.Domain.Participant(request.Participant.Id,
                request.Participant.Nume, request.Participant.Varsta));
            SwimResponse response = new SwimResponse();
            Participant p = new Participant();
            p.Id = participant.id;
            p.Nume = participant.nume;
            p.Varsta = participant.varsta;
            response.Participant = p;
            return Task.FromResult(response);
        }

        public override Task<SwimResponse> addInscriere(SwimRequest request, ServerCallContext context)
        {
            Console.WriteLine("add inscriere request..");
            var inscriere = inscriereRepository.Add(new SwimModel.Domain.Inscriere(request.Inscriere.IdParticipant,
                request.Inscriere.IdProba));
            foreach (String username in loggedUsers.Keys)
            {
                SwimResponse response = new SwimResponse();
                response.Update = true;
                Task.Run(() => loggedUsers[username].WriteAsync(response));
                Console.WriteLine("sent update response..");
                // await loggedUsers[username].WriteAsync(response);
            }
            return Task.FromResult(new SwimResponse() { });
        }

        public override Task<SwimResponse> nrParticipanti(SwimRequest request, ServerCallContext context)
        {
            Console.WriteLine("nr participanti request..");
            var nr = inscriereRepository.NrParticipanti(new SwimModel.Domain.Proba(request.Proba.Id,
                request.Proba.Distanta, request.Proba.Stil));

            return Task.FromResult(new SwimResponse()
            {
                Nr = nr
            });
        }

        public override Task<SwimResponse> getInscrisi(SwimRequest request, ServerCallContext context)
        {
            Console.WriteLine("get inscrisi request..");
            SwimResponse response = new SwimResponse();
            foreach (var participant in inscriereRepository.GetInscrisi(
                new SwimModel.Domain.Proba(request.Proba.Id, request.Proba.Distanta, request.Proba.Stil)))
            {
                Participant p = new Participant();
                p.Id = participant.id;
                p.Nume = participant.nume;
                p.Varsta = participant.varsta;
                response.Participanti.Add(p);
            }

            return Task.FromResult(response);
        }

        public override Task<SwimResponse> getInscrieri(SwimRequest request, ServerCallContext context)
        {
            Console.WriteLine("get inscrieri request..");
            SwimResponse response = new SwimResponse();
            foreach (var proba in inscriereRepository.GetInscrieri(new SwimModel.Domain.Participant(
                request.Participant.Id,
                request.Participant.Nume, request.Participant.Varsta)))
            {
                Proba p = new Proba();
                p.Id = proba.id;
                p.Distanta = proba.distanta;
                p.Stil = proba.stil;
                response.Probe.Add(p);
            }

            return Task.FromResult(response);
        }
    }
}